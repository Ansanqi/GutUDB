<script setup></script>

<template>
	<!-- <div class="footer">
		<div class="images">
			<img src="@/assets/images/bottomLogo.webp" />
			<div class="box">
				<img src="@/assets/images/bottomLogo2.webp" />
				<img src="@/assets/images/bottomLogo1.webp" />
				<img src="@/assets/images/bottomLogo3.webp" />
			</div>
		</div>
		<div class="split"></div>
		<div class="text">©2023 Microbiology Research Laboratory, West China Hospital&nbsp;<a target="_blank" href="https://beian.miit.gov.cn/">蜀ICP备16010396 </a></div>
	</div> -->
	<div class="footer">
			<div class="images">
				<div class="head_t">
					<img src="@/assets/images/biaoyu2.webp" />
					<img class="logo_bottom" src="@/assets/images/home_logo_bottom.webp" />
				</div>
				<div class="box">
					<img class="mr20" src="@/assets/images/gxyk_bg2.webp" />
					<img class="mr20" src="@/assets/images/sidx__bg2.webp" />
					<img src="@/assets/images/hxyy_bg2.webp" />
					<span>External <br>
						links</span>
					<a href="http://www.splicedb.net/casa/" target="_blank"><img src="@/assets/images/casc.webp" /></a>
				</div>
			</div>
			<div class="text">©2023 Microbiology Research Laboratory, West China Hospital&nbsp;<a target="_blank" href="https://beian.miit.gov.cn/">蜀ICP备16010396 </a></div>
		</div>
</template>

<style scoped lang="scss">
// .footer {
// 	height: 200px;
// 	background-image: url("@/assets/images/bottomBg.webp");
// 	background-size: cover;
// 	background-repeat: no-repeat;
// 	background-position: center;
// 	display: flex;
// 	flex-direction: column;
// 	.images {
// 		padding: 0 40px;
// 		flex: 1;
// 		display: flex;
// 		justify-content: space-between;
// 		align-items: center;
// 		img {
// 			height: 50px;
// 		}
// 		.box {
// 			display: flex;
// 			gap: 20px;
// 		}
// 	}
// 	.split {
// 		background-color: rgba(255, 255, 255, 0.2);
// 		height: 1px;
// 		width: 100%;
// 	}
// 	.text {
// 		display: flex;
// 		justify-content: center;
// 		align-items: center;
// 		font-size: $fontSize16;
// 		margin: 18px 0;
// 		font-weight: 500;
// 		color: #ffffff;
// 		a {
// 			color: white;
// 			text-decoration: none;
// 		}
// 	}
// }
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
		.head_t{
			position: relative;
			.logo_bottom{
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
