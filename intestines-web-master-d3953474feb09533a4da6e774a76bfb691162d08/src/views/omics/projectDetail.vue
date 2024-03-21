<script setup>
const route = useRoute()
const { projectNo } = route.query

import boxplotDotplot from "./projectDetail/boxplotDotplot.vue"
import barplot from "./projectDetail/barplot.vue"
import survivalAnalysis from "./projectDetail/survivalAnalysis.vue"

const cSample = ref(0)

const childRef1 = ref(null)
const childRef2 = ref(null)

const changeSample = (val) => {
	cSample.value = val
	// nextTick(() => {
	// 	childRef1.value.getList(samplesArr.value[cSample.value])
	// 	childRef2.value.getList(samplesArr.value[cSample.value])
	// })
	getList(samplesArr.value[cSample.value])
}

const samplesArr = ref([])

const survivalAnalysisD = ref("")

const d = ref({})
async function getData() {
	const { data } = await api.post(`/web/projectDetail/projectDetailByProjectNo?projectNo=${projectNo}`)
	d.value = data
	samplesArr.value = data.samples

	survivalAnalysisD.value = data.survivalAnalysis

	// if (data.samples?.length > 0) {
	nextTick(() => {
		childRef1.value.getList(samplesArr.value[cSample.value])
		childRef2.value.getList(samplesArr.value[cSample.value])
	})
	// }
	getList(samplesArr.value[cSample.value])
}
getData()

const tableData = ref([])
const pagination = reactive({
	current: 1,
	size: 5,
	total: 0,
})
const imgUrl = ref()
const imgUrl2 = ref()

async function getList(sample, order) {
	const {
		data: { records, total },
	} = await api.get("/web/geneExpressionData/page", { ...pagination, project: projectNo, sample, ...order })
	pagination.total = parseInt(total)
	tableData.value = records
	imgUrl.value = [picUrl(records[0].boxplotImgUrl), picUrl(records[0].dotplotImgUrl)]
	imgUrl2.value = [picUrl(records[0].tpmExpImg), picUrl(records[0].tsScoreImg)]
}
const cellMouseEnter = (row) => {
	imgUrl.value = [picUrl(row.boxplotImgUrl), picUrl(row.dotplotImgUrl)]
	imgUrl2.value = [picUrl(row.tpmExpImg), picUrl(row.tsScoreImg)]
}

const sortChange = async ({ column, prop, order }) => {
	getList(samplesArr.value[cSample.value], { orderBy: `${prop}_${order}` })
}

const goObj = ref({
	profile: [
		{
			name: "Boxplot&Dotplot",
			id: "profile-0",
		},
		{
			name: "Barplot",
			children: [
				{
					name: "TPM Exp",
					id: "profile-1-0",
				},
				{
					name: "TS Score",
					id: "profile-1-1",
				},
			],
		},
		// {
		// 	name: "Survival Analysis",
		// 	id: "profile-2",
		// },
	],
})

const go = (v, type) => {
	document.querySelector(`${v}`).scrollIntoView(type)
}
</script>

<template>
	<div class="el-backtop-wrap">
		<div class="el-backtop" @click="go('body', true)">
			<i class="el-icon el-backtop__icon">
				<svg viewBox="0 0 1024 1024" xmlns="http://www.w3.org/2000/svg">
					<path fill="currentColor" d="M512 320 192 704h639.936z"></path>
				</svg>
			</i>
		</div>
		<el-tooltip placement="left">
			<!-- trigger="click" -->
			<template #content>
				<div>
					<el-breadcrumb separator="|">
						<template v-for="v in goObj.profile">
							<el-breadcrumb-item v-if="v.id" @click="go('#' + v.id)" class="sss">{{ v.name }}</el-breadcrumb-item>
							<el-breadcrumb-item v-else>
								{{ v.name }}
								<template v-if="v.children" v-for="m in v.children">
									<div @click="go('#' + m.id)" class="sss">{{ m.name }}</div>
								</template>
							</el-breadcrumb-item>
						</template>
					</el-breadcrumb>
				</div>
			</template>
			<div class="el-backtop">GO</div>
		</el-tooltip>
		<div class="el-backtop" @click="go('body', false)">
			<i class="el-icon el-backtop__icon">
				<svg viewBox="0 0 1024 1024" xmlns="http://www.w3.org/2000/svg">
					<path fill="currentColor" d="m192 384 320 384 320-384z"></path>
				</svg>
			</i>
		</div>
	</div>

	<div class="content">
		<div class="summary mt20">
			<div class="title">Summary</div>
			<div class="con">
				<div class="item">
					<label>Project：</label><b style="color: #ff9900">{{ d.project }}</b>
				</div>
				<div class="item">
					<label>Disease：</label><b>{{ d.disease }}</b>
				</div>
				<div class="item">
					<label>Species：</label><b>{{ d.species }}</b>
				</div>
				<div class="item">
					<label>Tissue/Cell line：</label><b>{{ d.tissueCellLine }}</b>
				</div>
				<div class="item">
					<label>CASE：</label><b>{{ d.cases }}</b>
				</div>
				<div class="item">
					<label>Control：</label><b>{{ d.control }}</b>
				</div>
				<div class="item">
					<label>PMID：</label><b>{{ d.pmid }}</b>
				</div>
				<div class="item"><a :href="`https://www.ncbi.nlm.nih.gov/bioproject/?term=${d.project}`" target="_blank" class="link_url">NCBI</a></div>
			</div>
		</div>
		<!-- <div class="drugs">
			<div class="title">Drugs</div>

			<div class="my-nav">
				<template v-for="(v, index) in drugsNav" :key="v">
					<span :class="{ active: index === drugsIndex }" @click="drugsCurrentClick(index)">{{ v }}</span>
				</template>
			</div>
		</div> -->

		<el-tabs type="border-card">
			<el-tab-pane label="profile">
				<div class="sample df" v-if="samplesArr.length > 1">
					<div class="text mr20">Sample:</div>
					<div class="cup_wrap">
						<div class="btn cup" v-for="(item, i) in samplesArr" :class="{ isSelect: cSample == i }" @click="changeSample(i)">{{ item }}</div>
					</div>
				</div>
				<div class="my-table mt20">
					<div class="df jc_sb aic">
						<div class="tips">Gene expression data</div>
						<!-- <div class="my-tsv2" @click="down">
				<span class="iconfont icon-xiazai"></span>
				<span>CSV</span>
			</div> -->
					</div>
					<el-table :data="tableData" border stripe size="default" @sort-change="sortChange" @cell-mouse-enter="cellMouseEnter">
						<el-table-column type="index" label="#" width="55" />
						<el-table-column sortable="custom" show-overflow-tooltip prop="geneName" label="Gene Name" minWidth="120">
							<template #default="scope">
								<span v-if="scope.row.geneName === 'NA' || !scope.row.geneName">{{ scope.row.geneName }}</span>
								<router-link v-else :to="{ path: '/gene/index', query: { geneName: scope.row.geneName } }" class="table-link">{{ scope.row.geneName }}</router-link>
							</template>
						</el-table-column>
						<el-table-column sortable="custom" show-overflow-tooltip prop="ensembleId" label="Ensemble ID" minWidth="200" />
						<el-table-column sortable="custom" show-overflow-tooltip prop="meanCase" label="Mean (Case)" minWidth="150" >
							<template #default="scope">{{ parseFloat(scope.row.meanCase).toFixed(4) }}</template>
						</el-table-column>
						<el-table-column sortable="custom" show-overflow-tooltip prop="meanControl" label="Mean (Control)" minWidth="180" >
							<template #default="scope">{{ parseFloat(scope.row.meanControl).toFixed(4) }}</template>
						</el-table-column>
						<el-table-column sortable="custom" show-overflow-tooltip prop="log2FoldChange" label="Log2(Fold Change)" minWidth="120" >
							<template #default="scope">{{ parseFloat(scope.row.log2FoldChange).toFixed(4) }}</template>
						</el-table-column>
						<el-table-column sortable="custom" show-overflow-tooltip prop="pvalue" label="p.value" minWidth="150" >
							<template #default="scope">{{ parseFloat(scope.row.pvalue).toFixed(4) }}</template>
						</el-table-column>
					</el-table>
					<Pagination v-model:page="pagination.current" v-model:size="pagination.size" :total="pagination.total" @page-change="getList()"></Pagination>
				</div>
				<div class="item">
					<div class="title" id="profile-0">Boxplot&Dotplot</div>
					<div class="bottom mt20">
						<div class="left mr20" v-if="imgUrl">
							<div class="title2">Boxplot</div>
							<div class="images">
								<el-image :src="imgUrl[0]" v-if="imgUrl[0]">
									<template #error>
										<div class="image-slot">
											<el-empty description="No Data" />
										</div>
									</template>
								</el-image>
								<el-empty v-else />
							</div>
						</div>
						<div class="right" v-if="imgUrl">
							<div class="title2">Dotplot</div>
							<div class="images">
								<el-image :src="imgUrl[1]" v-if="imgUrl[1]">
									<template #error>
										<div class="image-slot">
											<el-empty description="No Data" />
										</div>
									</template>
								</el-image>
								<el-empty v-else />
							</div>
						</div>
					</div>
				</div>
				<div class="item">
					<div class="title">Barplot</div>
					<div class="bottom mt20">
						<div class="left mr20" v-if="imgUrl2">
							<div class="title2">TPM Exp</div>
							<div class="images">
								<el-image :src="imgUrl2[0]" v-if="imgUrl2[0]">
									<template #error>
										<div class="image-slot">
											<el-empty description="No Data" />
										</div>
									</template>
								</el-image>
								<el-empty v-else />
							</div>
						</div>
						<div class="right" v-if="imgUrl2">
							<div class="title2">TS Score</div>
							<div class="images">
								<el-image :src="imgUrl2[1]" v-if="imgUrl2[1]">
									<template #error>
										<div class="image-slot">
											<el-empty description="No Data" />
										</div>
									</template>
								</el-image>
								<el-empty v-else />
							</div>
						</div>
					</div>
					<!-- <barplot ref="childRef2" :projectNo="projectNo"></barplot> -->
				</div>
				<!-- <div class="item">
					<div class="title">Survival Analysis</div>
					<survivalAnalysis :survivalAnalysis="survivalAnalysisD" id="profile-2"></survivalAnalysis>
				</div> -->
			</el-tab-pane>
		</el-tabs>
	</div>
</template>

<style lang="scss" scoped>
@import url("@/styles/content.scss");
.title {
	padding-left: 10px;
	border-left: 4px solid $themeColor;
	margin-bottom: 10px;
	font-size: $fontSize20;
	font-weight: bold;
}
.content > div {
	margin-bottom: 40px;
}
.my-nav {
	margin-top: 30px;
}
.summary {
	.con {
		background: $backgroundColor;
		padding: 20px 30px;
		display: grid;
		grid-template-columns: 1fr 1fr 1fr;
		.item {
			margin-bottom: 10px;
			&.mb-0 {
				margin-bottom: 0;
			}
		}
	}
}

.button_wrap {
	display: flex;
	margin-bottom: 30px;
	gap: 20px;
	.button {
		cursor: pointer;
		color: white;
		padding: 10px;
		height: 40px;
		border-radius: 4px;
		background-color: rgba(3, 147, 174, 0.05);
		color: #333333;
	}
	.active {
		background-color: $themeColor;
		color: white;
	}
}
.tips {
	color: $themeColor;
	line-height: 3;
	margin-top: -20px;
}

.isSelect {
	padding-bottom: 5px;
	color: $themeColor;
	font-weight: bold;
	// border-bottom: 5px solid #0493ae;
}
.link_url {
	text-decoration: underline;
	color: $themeColor;
}
.sample {
	// .btn:first-child {
	// 	margin-left: 20px;
	// }
	.cup_wrap {
		color: #4c4c4c;
		display: flex;
		// justify-content: center;
		flex-wrap: wrap;
		gap: 10px;
	}
}
.el-backtop-wrap {
	position: fixed;
	right: 40px;
	top: 50%;
	width: 40px;
	transform: translateY(-50%);
	// height: 150px;
	display: flex;
	flex-direction: column;
	z-index: 1;
	.el-backtop {
		position: relative;
		margin-bottom: 20px;
	}
}
body {
	transition: all linear 2s;
}
.el-breadcrumb {
	::v-deep(.el-breadcrumb__item) {
		align-items: flex-start;
		line-height: 2;
		&.sss {
			.el-breadcrumb__inner {
				color: #fff;
				&:hover {
					color: $themeColor;
					cursor: pointer;
				}
			}
		}
		.sss {
			color: #fff;
			&:hover {
				color: $themeColor;
				cursor: pointer;
			}
		}
	}
}

.tips {
	color: $themeColor;
	line-height: 3;
}
.tips_wrap {
	margin-top: -20px;
}
.bottom {
	display: flex;
	justify-content: center;
	margin-top: 40px;
	color: $fontColor1;

	.left,
	.right {
		flex: 1;
		// width: 50%;
		display: flex;
		justify-content: center;
		flex-direction: column;
		.title2 {
			display: flex;
			justify-content: center;
			align-items: center;
			margin-bottom: 20px;
			font-size: $fontSize20;
			font-weight: bold;
		}
		.images {
			// width: 100%;
			display: flex;
			flex-direction: column;
			gap: 20px;
			// min-height: 300px;
			:deep(.el-image img) {
				// height: 500px;
			}
		}
	}
}
</style>
