<script setup>
import dayjs from "dayjs"

dayjs.locale("en")
const router = useRouter()
const searchText = ref("")

let dataT = []

const newsLeft = ref({
	title: "",
})
const newsRight = ref({
	title: "",
})

const isShow = ref(false)

const newsArr = ref([])
async function getData() {
	const { data } = await api.get("/web/searchHistory/hotSearch")
	dataT = data
	hotSearchArr.value = data.map((item) => item.searchText)
	const { data: data2 } = await api.get("/web/news/firstPageNews")

	if (data2.length > 3) {
		newsArr.value = [data2[0], data2[1], data2[2]]
	} else {
		if (data2.length > 0) {
			isShow.value = true
			newsArr.value = data2

			let arr = [{}, {}, {}]
			for (let i = 0; i < data2.length; i++) {
				const item = data2[i];
				arr[i] = item
			}
			newsArr.value = arr
		} else {
			isShow.value = false
		}
	}
}
getData()

const hotSearchArr = ref(["Hot search", "MGMT", "IDH1", "TP53", "EGFR", "PTEN", "Glioma", "Alzheimer's", "Disease"])

const search = async () => {
	if (!searchText.value) return

	const { data } = await api.get("/web/searchHistory/search", { searchText: searchText.value.trim() })
	if (data?.geneName) {
		router.push({ path: "/gene/index", query: { geneName: data.geneName } })
	} else {
		ElMessage({
			showClose: true,
			message: "未查询到结果",
			type: "error",
		})
	}
}
const searchClick = (i, v) => {
	if (dataT[i].aboutGene == v) {
		router.push({ path: "/gene/index", query: { geneName: dataT[i].aboutGene } })
	}
}

const animate = ref(true)
const stopAnimation = () => {
	animate.value = false
}
const startAnimation = () => {
	animate.value = true
}

const centerWrap = ref(null)
let circleCenterX = ""
let circleCenterY = ""
function getCenterPoint() {
	const divElement = centerWrap.value
	const rect = divElement.getBoundingClientRect()
	circleCenterX = rect.left + rect.width / 2
	circleCenterY = rect.top + window.scrollY + rect.height / 2
}
const handleClick = (event) => {
	const mouseX = event.pageX
	const mouseY = event.pageY

	getCenterPoint()

	if (mouseX < circleCenterX && mouseY < circleCenterY) {
		router.push("/species/index")
	} else if (mouseX < circleCenterX && mouseY >= circleCenterY) {
		router.push("/therapy/index/compounds")
	} else if (mouseX >= circleCenterX && mouseY < circleCenterY) {
		router.push("/diseases/index")
	} else {
		router.push("/omics")
	}
}

const goDetail = (id) => {
	router.push({ path: "/home/news/article", query: { id } })
}
</script>

<template>
	<div class="wrap">
		<div class="header">
			<Nav></Nav>
			<div class="search_wrap">
				<img src="@/assets/images/imdn_slogan.webp" />
				<div class="search_box">
					<img src="@/assets/images/searc_icon.webp" style="cursor: pointer" @click="search" />
					<input class="search_input" type="text" v-model="searchText" @keyup.enter="search" placeholder="Search all genes" />
				</div>
				<div class="search_box2">
					<div class="hot_search">
						<!-- <span v-for="(v, i) in hotSearchArr" :class="{ isYellow: i == 0 }" class="search_word" @click="searchClick(i,v)">{{ v }}</span> -->
						<span class="search_word isYellow">Hot search</span>
						<span v-for="(v, i) in hotSearchArr" class="search_word" @click="searchClick(i, v)">{{ v }}</span>
					</div>
				</div>
			</div>
		</div>

		<div class="center_data">
			<img src="@/assets/images/center_4.webp" object-fit="cover" />
			<img id="center_border" class="counterclockwiseRotation" src="@/assets/images/center_border.webp" />
			<div class="center3_wrap" ref="centerWrap" @mouseenter="stopAnimation" @mouseleave="startAnimation" @click="handleClick">
				<img class="center3" :class="{ clockwiseRotate: animate }" src="@/assets/images/center_3.webp" />
				<img class="center2 counterclockwiseRotation" src="@/assets/images/center_2.webp" />
				<img class="center1" src="@/assets/images/center_1.webp" />
				<!-- <div class="lt"></div>
				<div class="rt"></div>
				<div class="lb"></div>
				<div class="rb"></div> -->
			</div>
		</div>

		<img src="@/assets/images/home1.webp" style="width: 100%; object-fit: cover" />
		<div class="news_content" v-if="isShow">
			<img src="@/assets/images/home2.webp" style="width: 100%; object-fit: cover" />
			<div class="news_wrap">
				<template v-for="item in newsArr">
					<div class="news_box">
						<div class="news_t" v-if="item?.title">
							<div class="news_title">{{ item.title }}</div>
							<div class="news_content_t">
								{{ item.content }}
							</div>
							<div class="news_bottom">
								<div class="news_arrow" @click="goDetail(item.id)">
									<span class="iconfont icon-jiantou_xiangyou" style="color: #00c0c9; font-size: 20px"></span>
								</div>
								<div class="news_date">{{ item.published }} | {{ dayjs(item.createTime).format("DD MMMM YYYY") }}</div>
							</div>
						</div>
						<div class="news_t2" v-else>
							<img src="@/assets/images/nodata.webp" />
						</div>
					</div>
					<div class="verticalLine"></div>
				</template>
			</div>
		</div>
		<!-- <img src="@/assets/images/home3.webp" style="width: 100%; object-fit: cover" /> -->

		<!-- <div class="content_wrap">
			<template v-if="isShow">
				<div class="news_title">
					<div class="left">
						<div class="t" @click="$router.push('/home/news')">NEWS AND UPDATE ></div>
					</div>
					<div class="right">VISITS ></div>
				</div>
				<div class="separator_view2"></div>
				<div class="news_content">
					<div class="box">
						<div class="img_wrap">
							<img :src="picUrl(newsLeft.coverImg)" style="width: 100%" />
						</div>
						<div class="t1">{{ newsLeft.title }}</div>
						<div class="t2">{{ newsLeft.content }}</div>
						<div class="b2">{{ newsLeft.published }} | {{ dayjs(newsLeft.createTime).format("DD MMMM YYYY") }}</div>
					</div>
					<div class="box">
						<div class="img_wrap">
							<img :src="picUrl(newsRight.coverImg)" style="width: 100%" />
						</div>
						<div class="t1">{{ newsRight.title }}</div>
						<div class="t2">{{ newsRight.content }}</div>
						<div class="b2">{{ newsRight.published }} | {{ dayjs(newsRight.createTime).format("DD MMMM YYYY") }}</div>
					</div>
					<div class="news_right_image">
						<img src="@/assets/images/map.webp" />
						<div class="circle_wrap">
							<div class="point1"></div>
							<div class="text1">aerobe 234(20%)</div>
							<div class="point2"></div>
							<div class="text2">Alloiococcus otitidis 103(12%)</div>
							<div class="point3"></div>
							<div class="text3">Abiotrophia adjacens 103(12%)</div>
							<div class="point4"></div>
							<div class="text4">Opitutales 103(12%)</div>
						</div>
					</div>
				</div>
				<div class="separator_view1"></div>
			</template>

			<div class="cooperation_title">
				<div class="left">
					<div class="t2">COOPERATION PARTNERS ></div>
				</div>
				<div class="right"></div>
			</div>
			<div class="separator_view2"></div>
			<div class="cooperation_content">
				<img src="@/assets/images/partners1.webp" />
				<img src="@/assets/images/partners2.webp" />
				<img src="@/assets/images/partners3.webp" />
				<img src="@/assets/images/partners4.webp" />
				<img src="@/assets/images/partners5.webp" />
			</div>
		</div> -->
		<!-- <LayoutFooter></LayoutFooter> -->

		<div class="footer">
			<div class="images">
				<div class="head_t">
					<img src="@/assets/images/biaoyu2.webp" />
					<img class="logo_bottom" src="@/assets/images/home_logo_bottom.webp" />
				</div>
				<div class="box">
					<img src="@/assets/images/gxyk_bg2.webp" />
					<img src="@/assets/images/sidx__bg2.webp" />
					<img src="@/assets/images/hxyy_bg2.webp" />
					<span>External <br>
						links</span>
					<a href="http://www.splicedb.net/casa/" target="_blank"><img src="@/assets/images/casc.webp" /></a>
				</div>
			</div>
			<div class="text">©2023 Microbiology Research Laboratory, West China Hospital&nbsp;<a target="_blank" href="https://beian.miit.gov.cn/">蜀ICP备16010396 </a></div>
		</div>
	</div>
</template>

<style lang="scss" scoped>
$vm_width: 1920;
@function vw($px) {
	@return calc($px / $vm_width) * 100vw;
}
$vm_height: 1080;
@function vh($px) {
	@return calc($px / $vm_height) * 100vh;
}
@keyframes clockwiseRotate {
	0% {
		transform: translate(-50%, -50%) rotate(0deg);
	}
	100% {
		transform: translate(-50%, -50%) rotate(360deg);
	}
}
@keyframes counterclockwiseRotation {
	0% {
		transform: translate(-50%, -50%) rotate(0deg);
	}
	100% {
		transform: translate(-50%, -50%) rotate(-360deg);
	}
}
.clockwiseRotate {
	animation: clockwiseRotate 25s linear infinite;
}
.counterclockwiseRotation {
	animation: counterclockwiseRotation 12s linear infinite;
}
.wrap {
	.header {
		display: flex;
		flex-direction: column;
		align-items: center;
		background-image: url("@/assets/images/dh_nav_bg.webp");
		background-size: cover;
		background-repeat: no-repeat;
		background-position: center;
		vertical-align: middle;
		// :deep(.nav .right .link) {
		// 	font-weight: bold;
		// 	padding: 5px;
		// 	cursor: pointer;
		// 	margin-left: 15px;
		// 	font-size: $fontSize20;
		// 	color: rgba(254, 254, 254, 0.7);
		// }
		// :deep(.nav .right .link:hover){
		// 	color: white;
		// }
		.search_wrap {
			width: 100%;
			height: 180px;
			display: flex;
			flex-direction: column;
			justify-content: center;
			align-items: center;
			// margin-top: 18px;
			.search_box {
				margin-top: 10px;
				width: 650px;
				height: 50px;
				background: rgba(255, 255, 255, 0.8);
				border-radius: 25px;

				display: flex;
				align-items: center;
				justify-content: center;
				.search_input {
					font-size: $fontSize16;
					// flex: 1;
					height: 100%;
					border: none;
					background: transparent;
					padding-left: 10px;
					outline: none;
				}
				input::placeholder {
					color: #999; /* 更改为你想要的颜色 */
				}
			}
			.search_box2 {
				// width: 700px;
			}
			.hot_search {
				margin-top: 12px;
				display: flex;
				.search_word {
					font-size: $fontSize16;
					cursor: pointer;
					color: rgba(255, 255, 255, 0.5);
					padding: 5px 10px;
					margin-right: 10px;
					height: 20px;
					display: flex;
					align-items: center;
				}
				// .search_word:hover {
				// 	color: #ffd24a;
				// }
				.isYellow {
					color: rgba(255, 255, 255, 1);
					padding: 3px 12px;
					background: #fecd2f;
					border-radius: 10px;
				}
			}
		}
	}
	.center_data {
		position: relative;
		overflow: hidden;
		display: flex;
		justify-content: center;
		#center_border {
			width: vw(1530);
			pointer-events: none;
			user-select: none;
			position: absolute;
			top: 50%;
			left: 50%;
			transform: translate(-50%, -50%);
		}
		.center3_wrap {
			cursor: pointer;
			position: absolute;
			top: 50%;
			left: 50%;
			transform: translate(-50%, -50%);
			width: 921px;
			height: 921px;
			border-radius: 460.5px;
		}
		.center3 {
			pointer-events: none;
			user-select: none;
			position: absolute;
			top: 50%;
			left: 50%;
			transform: translate(-50%, -50%);
		}
		.center2 {
			pointer-events: none;
			user-select: none;
			position: absolute;
			top: 50%;
			left: 50%;
			transform: translate(-50%, -50%);
		}
		.center1 {
			pointer-events: none;
			user-select: none;
			position: absolute;
			top: 50%;
			left: 50%;
			transform: translate(-50%, -50%);
		}
	}
	.content_wrap {
		display: flex;
		flex-direction: column;
		padding: 0 80px;
		position: relative;
		.data_base {
			padding: 150px 0;
			display: flex;
			flex-direction: column;
			align-items: center;
			.title {
				font-size: 43px;
				letter-spacing: 4px;
				color: $fontColor1;
			}
			.sub_title {
				margin-top: 26px;
				font-size: $fontSize26;
				color: #0493ae;
			}
		}
		.data_base_data {
			height: 518px;
			display: flex;
			position: relative;
			justify-content: space-evenly;
			.data_base_data_bg {
				position: absolute;
				width: 1270px;
				height: 100%;
				top: 50%;
				left: 50%;
				transform: translate(-50%, -50%);
				background-image: url("@/assets/images/databasebg_img.webp");
				background-size: cover;
				background-repeat: no-repeat;
				background-position: center;
			}
			.box {
				display: flex;
				flex-direction: column;
				align-items: center;
				padding-top: 12px;
				.number {
					margin: 58px 0 104px 0;
					font-size: 54px;
					color: $themeColor;
				}
				.name {
					font-size: $fontSize24;
					color: $fontColor1;
				}
			}
		}
		.separator_view1 {
			height: 6px;
			background: #28a9c0;
		}
		.separator_view2 {
			height: 2px;
			background: #28a9c0;
		}
		.news_title,
		.cooperation_title {
			padding: 27px 0;
			display: flex;
			.left,
			.right {
				width: 50%;
				font-size: 43px;
				display: flex;
				height: 100%;
			}
			.t {
				padding: 0 20px;
				height: 100%;
				cursor: pointer;
			}
			.t2 {
				padding: 0 20px;
				height: 100%;
			}
		}
		.news_content {
			height: 800px;
			display: flex;
			justify-content: space-between;
			gap: 35px;
			align-items: center;
			.box {
				display: flex;
				flex-direction: column;
				.img_wrap {
					width: 412px;
					height: 228px;
					border-radius: 4px;
				}
				.t1 {
					margin: 38px 0 34px 0;
					font-size: $fontSize26;
					display: -webkit-box;
					-webkit-line-clamp: 2;
					-webkit-box-orient: vertical;
					overflow: hidden;
					text-overflow: ellipsis;
				}
				.t2 {
					display: -webkit-box;
					-webkit-box-orient: vertical;
					overflow: hidden;
					text-overflow: ellipsis;
					-webkit-line-clamp: 2; /* 显示两行文字 */
					// max-height: 3.6em; /* 控制最大高度，根据需要调整 */
					// line-height: 1.8em; /* 行高，根据需要调整 */
				}
				.b2 {
					margin: 132px 0 19px 0;
				}
				.t2,
				.b1,
				.b2 {
					font-size: $fontSize20;
					color: $fontColor2;
				}
			}
			.news_right_image {
				position: relative;
				.circle_wrap {
					position: absolute;
					left: 226px;
					top: -9px;
					width: 262px;
					height: 262px;

					background: rgba(255, 255, 255, 0.87);
					border: 2px dashed #00a3ac;
					border-radius: 50%;

					.point1 {
						position: absolute;
						top: 16px;
						left: 123px;
						width: 9px;
						height: 9px;
						background: #c94545;
						border-radius: 50%;
					}
					.text1 {
						position: absolute;
						top: 26px;
						left: 67px;
						font-size: $fontSize20;
						color: #c94545;
						white-space: nowrap;
					}
					.point2 {
						position: absolute;
						top: 82px;
						left: 187px;
						width: 9px;
						height: 9px;
						background: #f39c27;
						border-radius: 50%;
					}
					.text2 {
						position: absolute;
						top: 92px;
						left: 66px;
						font-size: $fontSize20;
						color: #f39c27;
						white-space: nowrap;
					}
					.point3 {
						position: absolute;
						top: 146px;
						left: 132px;
						width: 9px;
						height: 9px;
						background: #f39c27;
						border-radius: 50%;
					}
					.text3 {
						position: absolute;
						top: 156px;
						left: 11px;
						font-size: $fontSize20;
						color: #f39c27;
						white-space: nowrap;
					}
					.point4 {
						position: absolute;
						top: 199px;
						left: 113px;
						width: 9px;
						height: 9px;
						background: #6ca1ff;
						border-radius: 50%;
					}
					.text4 {
						position: absolute;
						top: 212px;
						left: 46px;
						font-size: $fontSize20;
						color: #6ca1ff;
						white-space: nowrap;
					}
				}
			}
		}
		.cooperation_content {
			height: 375px;
			display: flex;
			align-items: center;
			gap: 12px;
			justify-content: space-between;
		}
	}

	.news_content {
		position: relative;
		.news_wrap {
			width: vw(1510);
			height: 80%;
			position: absolute;
			top: 0px;
			left: 0px;
			display: flex;
			align-items: center;
			.news_box {
				flex: 1;
				display: flex;
				align-items: center;
				justify-content: center;
				.news_t {
					width: 380px;
					display: flex;
					flex-direction: column;
					.news_title {
						font-size: $fontSize22;
						color: #4c4c4c;
					}
					.news_content_t {
						margin-top: 15px;
						margin-bottom: 35px;
						font-size: $fontSize16;
						line-height: 28px;
						color: #999999;
						overflow: hidden; /* 隐藏溢出部分 */
						text-overflow: ellipsis; /* 显示省略号 */
						// max-height: 248px;
						// word-break: break-all;
						// text-align: justify;
						display: -webkit-box;
						-webkit-box-orient: vertical;
						-webkit-line-clamp: 8;
					}
					.news_bottom {
						display: flex;
						justify-content: space-between;
						.news_arrow {
							cursor: pointer;
						}
						.news_date {
							font-size: $fontSize16;
							color: #999999;
						}
					}
				}
				.news_t2 {
					width: 380px;
					display: flex;
					justify-content: center;
					align-items: center;
				}
			}
			.verticalLine {
				width: 1px;
				height: 300px;
				background: rgba(153, 153, 153, 0.5);
			}
			.verticalLine:last-child {
				display: none;
			}
		}
	}
}
.footer {
	height: 200px;
	background-image: url("@/assets/images/bottomBg.webp");
	background-size: cover;
	background-repeat: no-repeat;
	background-position: center;
	display: flex;
	flex-direction: column;
	.images {
		padding: 0 40px;
		flex: 1;
		display: flex;
		align-items: center;
		.head_t {
			position: relative;
			.logo_bottom {
				position: absolute;
				left: 6px;
				top: 0px;
			}
		}
		img {
			height: 50px;
		}
		.box {
			display: flex;
			align-items: center;
			// gap: 20px;
			span{
				margin-left: 20px;
				margin-right: -10px;
				font-size: $fontSize20;
				opacity: .9;
				color: #fff;
				font-weight: 400;
			}
		}
	}
	.split {
		background-color: rgba(255, 255, 255, 0.2);
		height: 1px;
		width: 100%;
	}
	.text {
		display: flex;
		justify-content: center;
		align-items: center;
		font-size: $fontSize16;
		margin: 18px 0;
		font-weight: 500;
		color: #ffffff;
		a {
			color: white;
			text-decoration: none;
		}
	}
}
</style>
