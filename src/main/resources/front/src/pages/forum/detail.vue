<template>
	<div class="forumdetail-preview">
		<div class="forumdetail-title">
			<div>浜ゆ祦璁哄潧</div>
		</div>
		<div class="back_box">
			<el-button class="backBtn" size="mini" @click="backClick">
				<span class="icon iconfont icon-fanhui01"></span>
				<span class="text">杩斿洖</span>
			</el-button>
		</div>

		<el-carousel class="forumdetail-carousel" v-if="detailBanner.length" trigger="click" indicator-position="inside" arrow="hover" type="default" direction="horizontal" height="400px" :autoplay="false" :interval="3000" :loop="true">
			<el-carousel-item v-for="(item, index) in detailBanner" :key="index">
				<img :preview-src-list="[item]" v-if="item.substr(0,4)=='http'" :src="item" class="image" @error="handleBannerError(index)">
				<img :preview-src-list="[baseUrl + item]" v-else :src="baseUrl + item" class="image" @error="handleBannerError(index)">
			</el-carousel-item>
		</el-carousel>
		<div class="section-content">
			<div class="content-title">{{detail.title}}</div>
			<div class="subhead-box">
				<div class="time_item" v-if="detail.addtime">
					<span class="icon iconfont icon-shijian21"></span>
					<span class="label">鍙戝竷鏃堕棿锛?/span>
					<span class="text">{{detail.addtime.split(' ')[0]}}</span>
				</div>
				<div class="publisher_item">
					<span class="icon iconfont icon-geren16"></span>
					<span class="label">鍙戝竷浜猴細</span>
					<span class="text">{{detail.isanon==1&&detail.userid!=userid?'鍖垮悕':detail.username}}</span>
				</div>
			</div>
			<div class="content-detail ql-snow ql-editor" v-html="detail.content"></div>
			<div class="comment-box">
				<div class="comment-top-box">
					<div class="comment-title">璇勮鍒楄〃</div>
					<el-button class="pubAdd" type="primary" @click="addComment">
						<span class="icon iconfont icon-tianjia14"></span>
						<span class="text">鐐瑰嚮璇勮</span>
					</el-button>
				</div>
				<div class="comment-list">
					<template v-if="commentList && commentList.length">
						<div  class="comment-item" v-for="item in commentList" :key="item.id" @mouseenter="commentEnter(item.id)" @mouseleave="commentLeave">
							<div class="comment-user">
								<img v-if="item.avatarurl" :src="$assetUrl(item.avatarurl)">
								<img v-if="!item.avatarurl" :src="require('@/assets/touxiang.png')">
								<div class="name">鐢ㄦ埛锛歿{item.username}}</div>
							</div>
							<div class="comment-content ql-snow ql-editor" v-html="item.content"></div>
							<div class="comment-btn">
								<el-button class="replyBtn" v-if="showIndex==item.id&&showIndex1 == -1" @click="replyClick(item.id)">鍥炲</el-button>
								<el-button class="delBtn" v-if="showIndex==item.id&&userid==item.userid&&showIndex1 == -1" @click="commentDel(item.id)">鍒犻櫎</el-button>
							</div>
							
							<template v-if="item.childs && item.childs.length">
								<div class="comment">
									<div class="item" v-for="items in item.childs" :key="items.id" @mouseenter="commentEnter1(items.id)" @mouseleave="commentLeave1">
										<div class="user">
											<img v-if="items.avatarurl" :src="$assetUrl(items.avatarurl)">
											<img v-if="!items.avatarurl" :src="require('@/assets/touxiang.png')">
											<div class="name">鐢ㄦ埛锛歿{items.username}}</div>
										</div>
										<div class="reply ql-snow ql-editor" v-html="items.content"></div>
										<div class="reply-btn">
											<el-button class="delBtn" v-if="showIndex==item.id&&userid==items.userid&&showIndex1==items.id" @click="commentDel(items.id)">鍒犻櫎</el-button>
										</div>
									</div>
								</div>
							</template>
						</div>
					</template>
				</div>
			</div>
		</div>
		<el-dialog title="娣诲姞璇勮" :visible.sync="dialogFormVisible">
			<el-form :model="form" :rules="rules" ref="form">
				<el-form-item label="璇勮" label-width="60px" prop="content">
					<editor
						myQuillEditor="content"
						style="width: 100%"
						v-model="form.content" 
						class="editor" 
						action="file/upload">
					</editor>
				</el-form-item>
			</el-form>
			<div slot="footer" class="dialog-footer">
				<el-button @click="dialogFormVisible = false">取消</el-button>
				<el-button type="primary" @click="addForum('form')">确定</el-button>
			</div>
		</el-dialog>
	</div>
</template>

<script>
	import Swiper from "swiper";
	export default {
		//鏁版嵁闆嗗悎
		data() {
			return {
				baseUrl: '',
				id: '',
				detail: {},
				commentList: [],
				dialogFormVisible: false,
				detailBanner: [],
				form: {
					content: '',
					parentid: '',
					userid: Number(localStorage.getItem('frontUserid')),
					username: localStorage.getItem('username'),
					avatarurl: '',
				},
				userid: Number(localStorage.getItem('frontUserid')),
				editorOption: {
					modules: {
						toolbar: [
							["bold", "italic", "underline", "strike"],
							["blockquote", "code-block"],
							[{ header: 1 }, { header: 2 }],
							[{ list: "ordered" }, { list: "bullet" }],
							[{ script: "sub" }, { script: "super" }],
							[{ indent: "-1" }, { indent: "+1" }],
							[{ direction: "rtl" }],
							[{ size: ["small", false, "large", "huge"] }],
							[{ header: [1, 2, 3, 4, 5, 6, false] }],
							[{ color: [] }, { background: [] }],
							[{ font: [] }],
							[{ align: [] }],
							["clean"],
							["image", "video"]
						]
					}
				},
				rules: {
					content: [
						{ required: true, message: '请输入评论', trigger: 'blur' }
					]
				},
				showIndex: -1,
				showIndex1: -1,
			}
		},
		created() {
			this.baseUrl = this.$config.baseUrl;
			this.id = this.$route.query.id
			this.getDetail()
		},
		mounted() {
		},
		//鏂规硶闆嗗悎
		methods: {
			// 杩斿洖鎸夐挳
			backClick(){
				history.back()
			},
			onEditorReady(editor) {
				editor.root.setAttribute('data-placeholder', "璇疯緭鍏ュ唴瀹?..");
			},
			getDetail() {
				this.$http.get(`forum/list/${this.id}`).then(res => {
					if (res.data.code == 0) {
						this.detailBanner = res.data.data.cover ? res.data.data.cover.split(",").filter(c => c.trim()) : [];
						res.data.data.content = res.data.data.content.replace(/img src/gi,"img style=\"width:100%;\" src");
						this.detail = res.data.data
						this.commentList = res.data.data.childs;
					}
				});
			},
			handleBannerError(index) {
				this.detailBanner.splice(index, 1);
			},
			// 鏂板璇勮
			addComment(){
				this.form.parentid = this.detail.id
				this.dialogFormVisible = true
			},
			// 榧犳爣绉诲叆
			commentEnter(index){
				this.showIndex = index
			},
			// 榧犳爣绉诲嚭
			commentLeave(){
				this.showIndex = -1
			},
			// 浜岀骇璇勮榧犳爣绉诲叆
			commentEnter1(index){
				this.showIndex1 = index
			},
			// 浜岀骇璇勮榧犳爣绉诲嚭
			commentLeave1(){
				this.showIndex1 = -1
			},
			// 鍒犻櫎璇勮
			commentDel(id){
				this.$confirm('鏄惁鍒犻櫎姝よ瘎璁猴紵')
				  .then(_ => {
					this.$http.post('forum/delete',[id]).then(res=>{
						if(res.data&&res.data.code==0){
							this.$message({
								type: 'success',
								message: '鍒犻櫎鎴愬姛!',
								duration: 1500,
								onClose: () => {
									this.getDetail();
								}
							});
						}
					})
				}).catch(_ => {});
			},
			// 鍥炲璇勮
			replyClick(id){
				this.form.parentid = id
				this.dialogFormVisible = true
			},
			addForum(formName) {
				this.$refs[formName].validate((valid) => {
					if (valid) {
						this.form.avatarurl = localStorage.getItem('frontHeadportrait')?localStorage.getItem('frontHeadportrait'):'';
						this.$http.post('forum/add', this.form).then(res => {
							if (res.data.code == 0) {
								this.$message({
									type: 'success',
									message: '璇勮鎴愬姛!',
									duration: 1500,
									onClose: () => {
										this.form.content = '';
										this.getDetail();
										this.dialogFormVisible = false;
									}
								});
							}
						});
					} else {
						return false;
					}
				});
			}
		}
	}
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
	.forumdetail-preview {
				padding: 20px 10%;
				margin: 20px auto;
				background: none;
				width: 100%;
				position: relative;
				.forumdetail-title {
						margin: 20px auto;
						width: 100%;
						div {
								padding: 0;
								color: #46AC2E;
								background: url() no-repeat left center / 40px 100%;
								font-weight: 600;
								display: block;
								width: 100%;
								font-size: 30px;
								border-color: #e61f4d;
								border-width: 0 0 0px;
								line-height: 40px;
								border-style: solid;
								text-align: center;
							}
		}
		.el-carousel.forumdetail-carousel {
			border-radius: 10px;
			margin: 0 0px 20px 0;
			background: rgba(255,255,255,.1);
			width: 100%;
			height: 400px;
			::v-deep .el-carousel__indicator button {
				width: 0;
				height: 0;
				display: none;
			}
			::v-deep .el-carousel__container {
				.el-carousel__arrow--left {
					display: block;
					width: 36px;
					font-size: 12px;
					height: 36px;
				}
				.el-carousel__arrow--left:hover {
					background: #e61f4d;
				}
				.el-carousel__arrow--right {
					display: block;
					width: 36px;
					font-size: 12px;
					height: 36px;
				}
				.el-carousel__arrow--right:hover {
					background: #e61f4d;
				}
				.el-carousel__item {
					border-radius: 10px;
					width: 100%;
					height: 100%;
					img {
						border: 0px solid #eee;
						border-radius: 10px;
						padding: 10px;
						object-fit: contain;
						width: 100%;
						height: 100%;
					}
				}
			}
		
			::v-deep .el-carousel__indicators {
				padding: 0;
				margin: 0 0 10px;
				z-index: 2;
				position: absolute;
				list-style: none;
				li {
					padding: 0;
					margin: 0 4px;
					background: #fff;
					display: inline-block;
					width: 12px;
					opacity: 0.4;
					transition: 0.3s;
					height: 12px;
				}
				li:hover {
					padding: 0;
					margin: 0 4px;
					background: #fff;
					display: inline-block;
					width: 12px;
					opacity: 0.7;
					height: 12px;
				}
				li.is-active {
					padding: 0;
					margin: 0 4px;
					background: #fff;
					display: inline-block;
					width: 12px;
					opacity: 1;
					height: 12px;
				}
			}
		}
		.section-content {
						border: 0px solid #eee;
						border-radius: 10px;
						padding: 10px;
						margin: 20px auto;
						background: none;
						width: 100%;
						position: relative;
						.content-title {
								padding: 20px 10px 0;
								color: #000;
								background: #fff;
								font-weight: 500;
								width: 100%;
								font-size: 20px;
								line-height: 50px;
								text-align: center;
								height: auto;
							}
			.subhead-box {
								background: #fff;
								display: flex;
								width: 100%;
								justify-content: center;
								align-items: center;
								.time_item {
										padding: 0 10px;
										color: #999;
										font-size: 15px;
										.icon {
												margin: 0 2px 0 0;
												line-height: 1.5;
											}
					.label {
												line-height: 1.5;
											}
					.text {
												line-height: 1.5;
											}
				}
				.publisher_item {
										padding: 0 10px;
										color: #999;
										font-size: 15px;
										.icon {
												margin: 0 2px 0 0;
												line-height: 1.5;
											}
					.label {
												line-height: 1.5;
											}
					.text {
												line-height: 1.5;
											}
				}
			}
			.content-detail {
								padding: 10px;
								color: #666;
								background: #fff;
								font-size: 16px;
								line-height: 1.8;
							}
			.comment-box {
								padding: 0;
								box-shadow: none;
								margin: 20px 0 0;
								background: none;
								width: 100%;
								.comment-top-box {
										padding: 5px 20px;
										color: #fff;
										display: flex;
										font-size: 26px;
										border-color: #eee;
										line-height: 1.5;
										border-radius: 4px;
										background: #46AC2E;
										width: 100%;
										justify-content: flex-end;
										border-width: 1px;
										align-items: center;
										border-style: solid;
										.comment-title {
												color: #fff;
												flex: 1;
												font-size: 20px;
											}
					.pubAdd {
												cursor: pointer;
												border: 0;
												padding: 0px;
												margin: 0 0px 0 0;
												color: #fff;
												font-size: 14px;
												border-color: #fff;
												line-height: 40px;
												border-radius: 0px;
												outline: none;
												background: none;
												width: auto;
												border-width: 0 0 2px;
												border-style: solid;
												height: 40px;
												.icon {
														margin: 0 0px 0 0;
														color: #fff;
														font-size: 16px;
													}
						.text {
														color: #fff;
														font-size: 15px;
													}
					}
					.pubAdd:hover {
												opacity: 1;
												.icon {
														color: #fff;
													}
						.text {
														color: #fff;
													}
					}
				}
				.comment-list {
										padding: 0;
										margin: 20px 0 0;
										background: #fff;
										width: 100%;
										.comment-item {
												padding: 0;
												margin: 0;
												color: #666;
												background: #fff;
												width: 100%;
												align-items: center;
												height: auto;
												.comment-user {
														padding: 10px;
														background: none;
														display: flex;
														width: 100%;
														border-color: #eee;
														border-width: 1px;
														align-items: center;
														border-style: solid;
														height: auto;
														img {
																border-radius: 100%;
																margin: 0 10px 0 0;
																object-fit: cover;
																width: 40px;
																height: 40px;
															}
							.name {
																color: #333;
																font-size: 16px;
															}
						}
						.comment-content {
														border-radius: 4px;
														padding: 10px 0;
														box-shadow: none;
														margin: 0;
														color: #666;
														background: none;
														font-size: 15px;
														line-height: 30px;
													}
						.comment-btn {
														margin: 0;
														display: flex;
														width: 100%;
														justify-content: flex-end;
														align-items: center;
														height: auto;
														.replyBtn {
																border: 0;
																cursor: pointer;
																border-radius: 4px;
																padding: 0 20px;
																margin: 0 10px;
																outline: none;
																color: #fff;
																background: #46AC2E;
																width: auto;
																font-size: 14px;
																line-height: 36px;
																height: 36px;
															}
							.delBtn {
																border: 0;
																cursor: pointer;
																border-radius: 4px;
																padding: 0 20px;
																margin: 0 10px;
																outline: none;
																color: #e61f4d;
																background: #e61f4d20;
																width: auto;
																font-size: 14px;
																line-height: 36px;
																height: 36px;
															}
						}
						.comment {
														padding: 0 0 0 40px;
														width: 100%;
														.item {
																padding: 8px 10px;
																margin: 10px 0;
																color: #666;
																background: #fff;
																width: 100%;
																border-color: #eee;
																border-width: 1px;
																align-items: center;
																border-style: solid;
																height: auto;
																.user {
																		border: none;
																		padding: 0;
																		background: none;
																		display: flex;
																		width: 100%;
																		align-items: center;
																		height: auto;
																		img {
																				border-radius: 100%;
																				margin: 0 10px 0 0;
																				object-fit: cover;
																				width: 40px;
																				height: 40px;
																			}
									.name {
																				color: #333;
																				font-size: 16px;
																			}
								}
								.reply {
																		border-radius: 4px;
																		padding: 0px;
																		box-shadow: none;
																		margin: 5px 0 0;
																		color: #666;
																		background: none;
																		font-size: 15px;
																		line-height: 30px;
																	}
								.reply-btn {
																		margin: 8px 0 0 0;
																		display: flex;
																		width: 100%;
																		justify-content: flex-end;
																		align-items: center;
																		height: 40px;
																		.delBtn {
																				border: 0;
																				cursor: pointer;
																				border-radius: 4px;
																				padding: 0 20px;
																				margin: 0 10px;
																				outline: none;
																				color: #e61f4d;
																				background: #e61f4d20;
																				width: auto;
																				font-size: 14px;
																				line-height: 36px;
																				height: 36px;
																			}
								}
							}
						}
					}
				}
			}
		}
	}
	.editor {
				border: 0px solid #ddd;
				border-radius: 4px;
				box-shadow: none;
				outline: none;
				margin: 30px 0 0 0;
				color: #333;
				width: 100%;
				clear: both;
				font-size: 14px;
				line-height: 32px;
			}
	.editor ::v-deep.ql-toolbar {
		background: none;
	}
	.editor ::v-deep.ql-container {
		background: none;
		min-height: 180px;
	}
	.editor ::v-deep.ql-container .ql-blank::before {
		color: #999;
	}
	video {
		border: 0;
		border-radius: 4px;
		margin: 10px auto;
		outline: none;
		display: block;
		width: 100%;
		order: 4;
	}
</style>

