package com.handler;

import com.utils.R;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.jdbc.UncategorizedSQLException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Global exception handler with API-safe payloads.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public R handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException e, HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        return withErrorDetail(
                R.error(String.format("请求地址 %s 不支持 %s 请求方式，请使用 %s",
                        requestURI, e.getMethod(), String.join(",", e.getSupportedMethods()))),
                e
        );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public R handleMethodArgumentNotValidException(MethodArgumentNotValidException e, HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        String message = e.getBindingResult().getFieldError() == null
                ? "参数校验失败"
                : e.getBindingResult().getFieldError().getDefaultMessage();
        return withErrorDetail(
                R.error(String.format("请求地址 %s 参数校验失败：%s", requestURI, message)),
                e
        );
    }

    @ExceptionHandler(BadSqlGrammarException.class)
    public R handleBadSqlGrammarException(BadSqlGrammarException e, HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        String errorMessage = DatabaseErrorParser.parseDatabaseErrorMessage(e);
        e.printStackTrace();
        return withErrorDetail(
                R.error(String.format("请求地址 %s 发生 SQL 语法错误：%s", requestURI, errorMessage)),
                e
        );
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public R handleDataIntegrityViolationException(DataIntegrityViolationException e, HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        String errorMessage = DatabaseErrorParser.parseDatabaseErrorMessage(e);
        e.printStackTrace();
        return withErrorDetail(
                R.error(String.format("请求地址 %s 数据操作异常：%s", requestURI, errorMessage)),
                e
        );
    }

    @ExceptionHandler(UncategorizedSQLException.class)
    public R handleUncategorizedSqlException(UncategorizedSQLException e, HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        String errorMessage = DatabaseErrorParser.parseDatabaseErrorMessage(e);
        e.printStackTrace();
        return withErrorDetail(
                R.error(String.format("请求地址 %s 数据库异常：%s", requestURI, errorMessage))
                        .put("remark", "请检查跨表关联或数据库权限配置"),
                e
        );
    }

    @ExceptionHandler(RuntimeException.class)
    public R handleRuntimeException(RuntimeException e, HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        e.printStackTrace();
        return withErrorDetail(
                R.error(String.format("请求地址 %s 发生运行时异常：%s", requestURI, e.getMessage())),
                e
        );
    }

    @ExceptionHandler(Exception.class)
    public R handleException(Exception e, HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        e.printStackTrace();
        return withErrorDetail(
                R.error(String.format("请求地址 %s 发生系统异常：%s", requestURI, e.getMessage())),
                e
        );
    }

    private R withErrorDetail(R response, Exception e) {
        String detail = e == null ? null : e.getClass().getSimpleName() + ": " + e.getMessage();
        return response.put("errorMsg", detail);
    }

    public static class DatabaseErrorParser {
        public static String parseDatabaseErrorMessage(Exception e) {
            String message = e.getMessage();

            if (e instanceof DuplicateKeyException) {
                return parseDuplicateKeyError(message);
            } else if (e instanceof DataIntegrityViolationException) {
                return parseDataIntegrityError(message);
            } else if (e instanceof BadSqlGrammarException) {
                return parseSQLError(message);
            } else if (e instanceof UncategorizedSQLException) {
                return parseUncategorizedSqlError(((UncategorizedSQLException) e).getMostSpecificCause().getMessage());
            }

            return "数据库操作异常：" + e.getClass().getSimpleName();
        }

        private static String parseDuplicateKeyError(String message) {
            if (message == null) {
                return "数据重复错误";
            }
            Pattern duplicatePattern = Pattern.compile("Duplicate entry '([^']+)' for key '([^']+)'");
            Matcher matcher = duplicatePattern.matcher(message);

            if (matcher.find()) {
                String duplicateValue = matcher.group(1);
                String keyInfo = matcher.group(2);
                String tableName = extractTableNameFromKey(keyInfo);
                String fieldName = extractFieldNameFromKey(keyInfo);

                if (tableName != null && fieldName != null) {
                    return String.format("表[%s]中字段[%s]的值 '%s' 已存在", tableName, fieldName, duplicateValue);
                }

                return String.format("值 '%s' 违反了唯一约束 '%s'", duplicateValue, keyInfo);
            }

            return "数据重复错误：违反了唯一性约束";
        }

        private static String parseDataIntegrityError(String message) {
            if (message == null) {
                return "数据完整性错误";
            }

            if (message.toLowerCase().contains("foreign key constraint fails")) {
                String foreignKeyInfo = extractForeignKeyInfo(message);
                if (foreignKeyInfo != null) {
                    return "外键约束错误：" + foreignKeyInfo;
                }
                return "外键约束错误：引用的数据不存在或已被删除";
            }

            if (message.contains("cannot be null") || message.contains("Field")) {
                String tableFieldInfo = extractTableFieldInfo(message);
                if (tableFieldInfo != null) {
                    return "必填字段错误：" + tableFieldInfo + " 字段不能为空";
                }
                return "必填字段错误：存在未填写的必填项";
            }

            if (message.contains("Data too long for column")) {
                String[] tableAndColumn = extractTableNameAndColumnFromLengthError(message);
                String tableName = tableAndColumn[0];
                String columnName = tableAndColumn[1];

                if (tableName != null && columnName != null) {
                    return String.format("表[%s]中字段[%s]的值超出最大允许长度", tableName, columnName);
                } else if (columnName != null) {
                    return String.format("字段[%s]的值超出最大允许长度", columnName);
                }
                return "字段长度超限错误";
            }

            return parseDuplicateKeyError(message);
        }

        private static String parseSQLError(String message) {
            if (message == null) {
                return "SQL 语法错误";
            }

            if (message.contains("Unknown column")) {
                String columnInfo = extractColumnInfo(message);
                String tableName = extractTableNameFromColumnError(message);

                if (columnInfo != null && tableName != null) {
                    return String.format("表[%s]中不存在字段[%s]", tableName, columnInfo);
                } else if (columnInfo != null) {
                    return "字段不存在：" + columnInfo;
                }
            }

            if (message.contains("Table") && message.contains("doesn't exist")) {
                String tableName = extractTableName(message);
                return tableName == null ? "数据表不存在" : "表不存在：" + tableName;
            }

            if (message.contains("View") && message.contains("doesn't exist")) {
                String viewName = extractViewName(message);
                return viewName == null ? "数据库视图不存在" : "视图不存在：" + viewName;
            }

            if (message.contains("PROCEDURE") && message.contains("does not exist")) {
                String procedureName = extractProcedureName(message);
                return procedureName == null ? "存储过程不存在" : "存储过程不存在：" + procedureName;
            }

            if (message.contains("You have an error in your SQL syntax")) {
                return "SQL 语句语法不正确";
            }

            return "SQL 语法错误";
        }

        private static String parseUncategorizedSqlError(String message) {
            if (message == null) {
                return "数据库操作异常";
            }

            if (message.contains("Access denied")) {
                return "数据库权限错误：当前用户没有执行此操作的权限";
            }

            if (message.contains("Communications link failure")
                    || message.contains("Connection refused")
                    || message.contains("No suitable driver found")) {
                return "数据库连接错误：无法连接到数据库服务器";
            }

            return parseSQLError(message);
        }

        private static String extractColumnInfo(String message) {
            Pattern pattern = Pattern.compile("Unknown column '([^']+)'");
            Matcher matcher = pattern.matcher(message);
            return matcher.find() ? matcher.group(1) : null;
        }

        private static String[] extractTableNameAndColumnFromLengthError(String message) {
            Pattern pattern = Pattern.compile("Data too long for column '([^']+)'[\\s\\S]*?INSERT INTO\\s+([^\\s(]+)");
            Matcher matcher = pattern.matcher(message);

            if (matcher.find()) {
                return new String[]{matcher.group(2), matcher.group(1)};
            }

            String columnName = extractColumnNameFromLengthError(message);
            return new String[]{null, columnName};
        }

        private static String extractTableNameFromColumnError(String message) {
            Pattern pattern = Pattern.compile("Unknown column '[^']+' in '([^']+)'");
            Matcher matcher = pattern.matcher(message);
            return matcher.find() ? matcher.group(1) : null;
        }

        private static String extractTableName(String message) {
            Pattern pattern = Pattern.compile("Table '([^']+)'");
            Matcher matcher = pattern.matcher(message);
            return matcher.find() ? matcher.group(1) : null;
        }

        private static String extractViewName(String message) {
            Pattern pattern = Pattern.compile("View '([^']+)'");
            Matcher matcher = pattern.matcher(message);
            return matcher.find() ? matcher.group(1) : null;
        }

        private static String extractColumnNameFromLengthError(String message) {
            Pattern pattern = Pattern.compile("Data too long for column '([^']+)'");
            Matcher matcher = pattern.matcher(message);
            return matcher.find() ? matcher.group(1) : null;
        }

        private static String extractProcedureName(String message) {
            Pattern pattern = Pattern.compile("PROCEDURE ([^\\s]+)");
            Matcher matcher = pattern.matcher(message);
            return matcher.find() ? matcher.group(1) : null;
        }

        private static String extractTableFieldInfo(String message) {
            Pattern fieldPattern = Pattern.compile("Field '([^']+)'");
            Matcher matcher = fieldPattern.matcher(message);
            return matcher.find() ? matcher.group(1) : null;
        }

        private static String extractForeignKeyInfo(String message) {
            Pattern pattern = Pattern.compile("CONSTRAINT `([^`]+)` FOREIGN KEY \\(`([^`]+)`\\) REFERENCES `([^`]+)` \\(`([^`]+)`\\)");
            Matcher matcher = pattern.matcher(message);

            if (matcher.find()) {
                String constraintName = matcher.group(1);
                String childField = matcher.group(2);
                String parentTable = matcher.group(3);
                String parentField = matcher.group(4);
                return String.format("约束[%s]：子表字段[%s]引用父表[%s]的字段[%s]不存在", constraintName, childField, parentTable, parentField);
            }
            return null;
        }

        private static String extractTableNameFromKey(String keyInfo) {
            if (keyInfo != null && keyInfo.contains(".")) {
                return keyInfo.split("\\.")[0];
            }
            return null;
        }

        private static String extractFieldNameFromKey(String keyInfo) {
            if (keyInfo != null && keyInfo.contains(".")) {
                String fieldName = keyInfo.split("\\.")[1];
                if (fieldName.endsWith("_UNIQUE") || fieldName.endsWith("_UK")) {
                    return fieldName.substring(0, fieldName.lastIndexOf('_'));
                }
                return fieldName;
            }
            return keyInfo;
        }
    }
}
