<script setup>
import { useDownload } from "@/hooks/useDownload"
const router = useRouter()
const tipsList = ref([
	{ label: "A pan-cancer blueprint of the heterogeneous tumor microenvironment revealed by single-cell profiling", link: "https://pubmed.ncbi.nlm.nih.gov/32561858/" },
	{ label: "Lineage tracking reveals dynamic relationships of T cells in colorectal cancer", link: "https://pubmed.ncbi.nlm.nih.gov/30479382/" },
	{ label: "Lineage-dependent gene expression programs influence the immune landscape of colorectal cancer", link: "https://pubmed.ncbi.nlm.nih.gov/32451460/" },
	{ label: "Molecular Pathways of Colon Inflammation Induced by Cancer Immunotherapy.", link: "https://pubmed.ncbi.nlm.nih.gov/32603654/" },
	{ label: "Peripheral T cell expansion predicts tumour infiltration and clinical response", link: "https://pubmed.ncbi.nlm.nih.gov/32103181/" },
	{ label: "Single-Cell Analyses Inform Mechanisms of Myeloid- Targeted Therapies in Colon Cancer", link: "https://pubmed.ncbi.nlm.nih.gov/32302573/" },
])
const list = ref([])
const src = ref()

getList()
async function getList(order) {
	const { data } = await api.get("/web/singleCellGeneExpressData/uniqueIdList")
	list.value = data
	src.value = data[0].imgUrl ? picUrl(data[0].imgUrl) : ""
}
const sortChange = async ({ column, prop, order }) => {
	getList({ orderBy: `${prop}_${order}` })
}
</script>

<template>
	<div class="content">
		<Title />
		<div class="con df jc_sb">
			<div class="l">
				<template v-for="v in list" :key="v">
					<div class="item df jc_sb aic" @mouseover="src = picUrl(v.imgUrl)" @click="router.push('/omics/single-cell-omics/gene-expression/details?id=' + v.uniqueId)">
						<el-text truncated>{{ v.uniqueId }}</el-text>
						<el-image :src="picUrl(v.imgUrl)" />
					</div>
				</template>
			</div>
			<div class="r df_jcc_aic fdc">
				<!-- <el-image style="width: 100px; height: 100px" :src="url" :fit="fit" /> -->
				<el-image :src="src" alt="" srcset="">
					<template #error>
						<div class="image-slot">
							<el-empty />
						</div>
					</template>
				</el-image>
				<div v-if="src" class="el-button" @click="useDownload(src)">
					<span class="iconfont icon-xiazai" style="margin-right: 5px"></span>
					<span>Download</span>
				</div>
				<!-- <div class="down df_jcc_aic" @click="useDownload(src)">Download</div> -->
			</div>
		</div>
		<div class="tips mt30">
			<div class="title mb20">Latest News/Articles:</div>
			<div class="item" v-for="(v, index) in tipsList" :key="v.label">
				<el-link :href="v.link" target="_blank">{{ index + 1 }}. {{ v.label }}</el-link>
			</div>
		</div>
	</div>
</template>

<style lang="scss" scoped>
@import url("@/styles/content.scss");
.con {
	.l {
		width: 30%;
		.item {
			height: 50px;
			padding: 10px;
			border: 1px solid $separatorColor;
			border-radius: 5px;
			cursor: pointer;
			margin-bottom: 10px;
			&:hover {
				border-color: $themeColor;
			}
			.el-image {
				height: 40px;
			}
		}
	}
	.r {
		flex: 1;
	}
}
.tips {
	.title {
		display: flex;
		align-items: center;
		font-size: $fontSize18;

		&::before {
			display: inline-block;
			content: "";
			width: 10px;
			height: 10px;
			border-radius: 50%;
			background-color: $fontColor1;
			margin-right: 10px;
		}
	}
	.item {
		line-height: 2;
	}
}
.down {
	width: 120px;
	height: 30px;
	background-color: rgba(3, 147, 174, 0.109803921568627);
	border-radius: 15px;
	cursor: pointer;
}
</style>
