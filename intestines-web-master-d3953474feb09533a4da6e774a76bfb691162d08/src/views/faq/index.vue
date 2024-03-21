<script setup>
const active = ref(0)

const arr = ref(["About IMDB", "In IMDB, users can"])
</script>

<template>
	<div class="wrap">
		<div class="headers">
			<LayoutNav></LayoutNav>
			<LayoutHeader />
		</div>
		<div class="content_wrap">
			<!-- <div class="left">
				<div v-for="(d, i) in arr" class="menu" :class="{ select: active == i }" @click="active = i">{{ d }}</div>
			</div> -->
			<div class="right">
				<Title title="About IMDB" />
				<div class="content1">
					<div class="a">
						<p>
							The analysis of a large amount of new sequencing data has posed significant challenges for biologists studying intestinal-related research and clinicians researching intestinal diseases. To facilitate direct access to various omics data related to intestinal diseases and advance research in this field, we have collected and processed genomics, transcriptomics, epigenomics, proteomics, metabolomics, microbiomics, as well as single cell omics and spatial omics data for various intestinal diseases across six species. Subsequently, we constructed a comprehensive universe of gut-related omics data IMDB, integrating clinical information such as diagnosis and drug-related data. IMDB also provides the drugs and diagnosis targets information of intestinal diseases. Overall, IMDB provide a comprehensive, systematic and practical platform for researchers, and dedicated to the management, comprehensive analysis and visualisation of knowledge related to intestinal diseases. Based on this database, various omics data analyses are carried out to explore the all genes of a certain intestinal disease. The first tool for comprehensive omics data research on intestinal diseases will help every researchers better understand intestinal diseases.
						</p>

					</div>
				</div>

				<Title title="In IMDB, users can" />
				<div class="content1">
					<div class="browse">
						<div class="party df">
							<div class="lf">1</div>
							<div class="rt">Browse or search for biological information on knowledge of every genes involved in intestinal diseases;</div>
						</div>
						<div class="party df">
							<div class="lf">2</div>
							<div class="rt">
								Browse multi-omics data related to intestinal diseases, including epigenomics, transcriptomics,genomics, monomics, proteomics, proteomics, metabolomics,
								microbiomics;
							</div>
						</div>
						<div class="party df">
							<div class="lf">3</div>
							<div class="rt">Browse multi-omics information on different species including homo sapiens, mus musculus, sus scrofa,etc; </div>
						</div>
						<div class="party df">
							<div class="lf">4</div>
							<div class="rt">Browse information on a wide range of intestinal diseases, such as disease-associated genes,etc;</div>
						</div>
						<div class="party df">
							<div class="lf">5</div>
							<div class="rt">Browse diverse therapeutic approaches related to intestinal disorders including chemical compounds,Traditional Medicine and probiotics;</div>
						</div>
						<div class="party df">
							<div class="lf">6</div>
							<div class="rt">
								Conduct case studies based on the developed database to explore the key genes (transcript factors or RNA binding proteins) of a certain intestinal disease, such as
								colon cancer, rectal cancer, etc.;
							</div>
						</div>
						<div class="party df">
							<div class="lf">7</div>
							<div class="rt">ChatGTP features an online interactive dialogue to answer users' questions;</div>
						</div>
						<div class="party df">
							<div class="lf">8</div>
							<div class="rt">Download all results and data for statistical analyses and case studies.</div>
						</div>
					</div>
				</div>
			</div>
			<div class="img">
				<img src="../../assets/images/faqBg.webp" alt="" srcset="" />
			</div>

		</div>
		<LayoutFooter></LayoutFooter>
	</div>
</template>

<style lang="scss" scoped>
.browse {
	display: flex;
	flex-direction: column;
	.lf {
		width: 24px;
		height: 24px;
		min-width: 24px;
		display: flex;
		align-items: center;
		justify-content: center;
		background: url(../../assets/images/szbg.webp);
		background-size: 24px 24px;
		margin-right: 5px;
		color: #fff;
	}
	.rt {
	}
}
.sx {
	display: inline-block;
	width: 12px;
}
.wrap {
	.headers {
		display: flex;
		flex-direction: column;
		.header_img {
			object-fit: cover;
			height: 200px;
		}
	}
	.content_wrap {
		display: flex;
		padding: 0 40px 40px;
		position: relative;
		// .left {
		// 	display: flex;
		// 	flex-direction: column;
		// 	background-color: white;
		// 	padding: 20px;
		// 	align-self: flex-start;
		// 	.menu {
		// 		cursor: pointer;
		// 		margin-bottom: 20px;
		// 		width: 150px;
		// 		height: 26px;
		// 		border-radius: 5px;
		// 		padding-left: 15px;
		// 		display: flex;
		// 		align-items: center;
		// 		position: relative;
		// 	}
		// 	.menu::after {
		// 		content: "";
		// 		position: absolute;
		// 		left: -4px;
		// 		width: 4px;
		// 		height: 26px;
		// 		background-color: rgba(242, 242, 242, 1);
		// 	}
		// 	.menu.select {
		// 		background-color: rgba(3, 147, 174, 0.109803921568627);
		// 		color: $themeColor;
		// 	}
		// 	.menu.select::after {
		// 		background-color: rgba(3, 147, 174, 1);
		// 	}
		// }
		.right {
			// height: 700px;
			// margin-left: 20px;
			display: flex;
			flex: 1;
			flex-direction: column;
			background-color: white;
			overflow-y: auto;
			padding: 20px;
			display: flex;
			flex-direction: column;
			.title {
				display: flex;
				align-items: center;
				padding-left: 15px;
				position: relative;
				height: 26px;
				color: $themeColor;
			}
			.title::after {
				content: "";
				position: absolute;
				left: -4px;
				width: 4px;
				height: 26px;
				background-color: rgba(3, 147, 174, 1);
			}
			.content1 {
				padding: 10px;
				font-size: $fontSize14;
				line-height: 20px;
				margin-top: -20px;
				line-height: 2;
				.a {
					margin-bottom: 30px;
					display: flex;

					p {
						padding: 5px 10px;
						text-align: justify;
					}
					img {
						height: 250px;
						margin: 0 150px;
					}
				}
			}
		}
		.img{
			background-color: #fff;
			display: flex;
			justify-content: center;
			align-items: center;
			padding: 0 20px;
			margin-left: 20px;
			img{
				width: 90%;
			}
		}
	}
}
</style>
