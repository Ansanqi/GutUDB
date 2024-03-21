<script setup>
const router = useRouter()

const txtArr = ["Epigenomics", "Genomics", "Transcriptomics", "Spatial omics", "Single cell omics", "Proteomics", "Microbiomics", "Metabolomics"]
const goDetail = (index) => {
	switch (index) {
		case 0:
			router.push("/omics/epigen-omics/chromosome-structure")
			break
		case 1:
			router.push("/omics/genomics/genomic-alteration")
			break
		case 2:
			router.push("/omics/transcriptomics/alternative-splicing")
			break
		case 3:
			router.push("/omics/spatial-omics")
			break
		case 4:
			router.push("/omics/single-cell-omics/gene-expression")
			break
		case 5:
			router.push("/omics/prote-omics")
			break
		case 6:
			router.push("/omics/microbi-omics/associated-genera")
			break
		case 7:
			router.push("/omics/metabol-omics")
			break
	}
}
const sortChange = async ({ column, prop, order }) => {
	getList({ orderBy: `${prop}_${order}` })
}
</script>

<template>
	<div class="content2">
		<!-- <div class="box">
			<div v-for="(v, i) in txtArr" :key="v" class="image_wrap" @click="goDetail(i)">
				<img :src="getSrc(`omics${i + 1}.webp`)" />
				<div class="text">{{ v }}</div>
			</div>
		</div> -->
		<div class="big_image">
			<img class="bg_img" src="@/assets/images/omics_bg.webp" />
			<div class="omics_w">
				<div class="omics_t">
					<div class="t1 t" @click="goDetail(0)">
						<img src="@/assets/images/omics1.webp" />
						<div style="margin-top: 25px">EPIGENOMICS</div>
					</div>
					<div class="t2 t" @click="goDetail(1)">
						<img src="@/assets/images/omics2.webp" />
						<div style="margin-top: 25px">GENOMICS</div>
					</div>
					<div class="t3 t" @click="goDetail(2)">
						<img src="@/assets/images/omics3.webp" />
						<div style="margin-top: 25px">TRANSCRIPTOMICS</div>
					</div>
					<div class="t4 t" @click="goDetail(3)">
						<img src="@/assets/images/omics4.webp" />
						<div style="margin-top: 25px">SPATIAL OMICS</div>
					</div>
				</div>
				<div class="omics_b">
					<div class="t5 t" @click="goDetail(4)">
						<img src="@/assets/images/omics5.webp" />
						<div style="margin-top: 25px">SINGLE CELL OMICS</div>
					</div>
					<div class="t6 t" @click="goDetail(5)">
						<img src="@/assets/images/omics6.webp" />
						<div style="margin-top: 25px">PROTEOMICS</div>
					</div>
					<div class="t7 t" @click="goDetail(6)">
						<img src="@/assets/images/omics7.webp" />
						<div style="margin-top: 25px">MICROBIOMICS</div>
					</div>
					<div class="t8 t" @click="goDetail(7)">
						<img src="@/assets/images/omics8.webp" />
						<div style="margin-top: 25px">METABOLOMICS</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</template>

<style lang="scss" scoped>
@keyframes showOrHide {
	0% {
		opacity: 1;
	}
	50% {
		opacity: 0;
	}
	100% {
		opacity: 1;
	}
}
.content2 {
	margin: 0 40px 40px;
	background-color: white;
	border-radius: 4px;
	/* padding: 40px; */
	// height: 604px;

	// border-radius: 251px;
	// display: flex;
	// align-items: center;
	// justify-content: center;
	// .box {
	// 	width: 100%;
	// 	display: grid;
	// 	gap: 30px;
	// 	grid-template-columns: repeat(4, 1fr);
	// 	img {
	// 		max-height: 100%; /* 图片高度最大为父级容器的高度 */
	// 		max-width: 100%; /* 图片宽度最大为父级容器的宽度 */
	// 		object-fit: contain; /* 图片等比例缩放，保持完整显示 */
	// 	}
	// 	.image_wrap {
	// 		cursor: pointer;
	// 		display: flex;
	// 		flex-direction: column;
	// 		align-items: center;
	// 		.text {
	// 			margin-top: 30px;
	// 			font-weight: bold;
	// 		}
	// 	}
	// }
	.big_image {
		position: relative;
		// width: 100%;
		// height: 214px;
		height: 604px;
		font-weight: 400;
		color: #4c4c4c;

		// background-image: url("@/assets/images/omics_bg.webp");
		// background-size: cover;
		// background-repeat: no-repeat;
		// background-position: center;

		.omics_w {
			position: absolute;
			z-index: 2;
			top: 0;
			left: 0;
			width: 100%;
			height: 100%;
			display: flex;
			flex-direction: column;
			justify-content: center;
			.omics_t,
			.omics_b {
				display: flex;
				justify-content: space-evenly;
			}
			.omics_b{
				margin-top: 30px;
			}
		}
	}
	.bg_img {
		position: absolute;
		z-index: 1;
		top: 50%;
		left: 0%;
		width: 100%;
		transform: translate(0%, -50%);
	}
	.t {
		display: flex;
		flex-direction: column;
		align-items: center;
		cursor: pointer;
		img {
			// width: 168px;
			// height: 168px;
			// border-radius: 50%;
			// overflow: hidden;
			// object-fit: cover;
			// box-shadow: 0px 12px 38px 0px rgba(112, 112, 112, 0.14);
		}
		div {
			// border: 2px solid #00c0c9;
			// border-radius: 17.5px;
			height: 35px;
			padding: 0 14px;
			font-size: $fontSize22;
			display: flex;
			align-items: center;
		}
	}
	.t:hover {
		color: $themeColor;
	}
}
</style>
