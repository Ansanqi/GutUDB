<script setup></script>

<template>
	<div class="wrap">
		<div class="headers">
			<LayoutNav></LayoutNav>
			<LayoutHeader />
		</div>
		<div class="content_wrap">
			<div class="title">
				We'd love to hear your feedback, suggestions, or reports about bugs or inconsistencies, so please don't hesitate to contact us if you have any questions.
			</div>
			<img src="../../assets/images/contactBg.webp" alt="" srcset="" />
			<!-- <img src="../../assets/images/contactBg1.webp" alt="" srcset=""> -->
			<div class="con">
				<div class="address item">
					<div class="img">
						<span class="iconfont icon-dizhi"></span>
					</div>
					<div class="r">
						<div class="top">Address</div>
						<div class="txt">
							<span>No. 22, Shuangyong Road, Qingxiu District, Nanning City, Guangxi Zhuang Autonomous Region</span>
						</div>
					</div>
				</div>
				<div class="email item">
					<div class="img">
						<span class="iconfont icon-youxiang"></span>
					</div>
					<div class="r">
						<div class="top">Email</div>
						<div class="txt">
							<span>Sanqi An, ansq@mail2.sysu.edu.cn</span>
							<span>Liang Hao, lianghao@gxmu.edu.cn</span>
							<span>Weimin Li, weimi003@scu.edu.cn</span>
							<span>Yaxin Chen, chenyaxin@scu.edu.cn</span>
						</div>
					</div>
				</div>
			</div>
		</div>
		<LayoutFooter></LayoutFooter>
	</div>
</template>

<style lang="scss" scoped>
.wrap {
	.headers {
		display: flex;
		flex-direction: column;
		.header_img {
			object-fit: cover;
			height: 251px;
		}
	}
	.content_wrap {
		background-color: #fff;
		margin: 0 40px 40px;
		padding-top: 30px;
		.title {
			font-size: $fontSize20;
			text-align: center;
		}
		img {
			width: 100%;
		}
		.con {
			display: flex;
			padding: 0 20px;
			margin-top: 40px;
			.item {
				display: flex;
				&.address {
					flex: 1;
					margin-right: 100px;
				}
				&.email {
					flex: 2;
					.txt {
						display: grid;
						grid-template-columns: 1fr 1fr;
					}
				}
				.img {
					min-width: 55px;
					height: 55px;
					border-radius: 50%;
					background: rgba(0, 192, 201, 0.05);
					display: flex;
					align-items: center;
					justify-content: center;
					margin-right: 25px;
					span {
						color: $themeColor;
						font-size: $fontSize22;
					}
				}
				.r {
					width: 100%;
					.top {
						color: $fontColor1;
						font-size: $fontSize20;
						font-weight: 500;
						font-family: "AlibabaPuHuiTi-Medium";
						margin-bottom: 20px;
					}
					.txt {
						color: #999;
						font-size: $fontSize16;
						font-weight: 400;
						margin-bottom: 40px;
						line-height: 2;
						span:hover {
							color: $themeColor;
						}
					}
				}
			}
		}
	}
}
</style>
