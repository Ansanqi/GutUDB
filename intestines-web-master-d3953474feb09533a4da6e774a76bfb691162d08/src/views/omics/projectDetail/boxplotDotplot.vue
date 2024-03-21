<script setup>
const props = defineProps({
	projectNo: String,
})

// 分页
const pagination = reactive({
	current: 1,
	size: 5,
	total: 0,
})

const leftArr = ref([])
const rightArr = ref([])

const tableData = ref([])

// getList()
async function getList(sample, order) {
	const {
		data: { records, total },
	} = await api.get("/web/geneExpressionData/page", { ...pagination, project: props.projectNo, sample, ...order })
	pagination.total = parseInt(total)
	tableData.value = records

	// leftArr.value = records.map((item) => item.boxplotImgUrl)
	// rightArr.value = records.map((item) => item.dotplotImgUrl)
	imgUrl.value = [picUrl(records[0].boxplotImgUrl), picUrl(records[0].dotplotImgUrl)]
}

defineExpose({ getList })

const down = async () => {
	await api.down("/web/geneExpressionData/exportExcel", { ...pagination,project: props.projectNo })
}
const sortChange = async ({ column, prop, order }) => {
	getList("", { orderBy: `${prop}_${order}` })
}

const imgUrl = ref()
const cellMouseEnter = (row) => {
	imgUrl.value = [picUrl(row.boxplotImgUrl), picUrl(row.dotplotImgUrl)]
}
</script>

<template>
	<div class="my-table">
		<div class="df jc_sb aic tips_wrap">
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
			<el-table-column sortable="custom" show-overflow-tooltip prop="meanCase" label="Mean (Case)" minWidth="150" />
			<el-table-column sortable="custom" show-overflow-tooltip prop="meanControl" label="Mean (Control)" minWidth="180" />
			<el-table-column sortable="custom" show-overflow-tooltip prop="log2FoldChange" label="Log2(Fold Change)" minWidth="120" />
			<el-table-column sortable="custom" show-overflow-tooltip prop="pvalue" label="p.value" minWidth="150" />
		</el-table>
		<Pagination v-model:page="pagination.current" v-model:size="pagination.size" :total="pagination.total" @page-change="getList()"></Pagination>

		<!-- <img :src="imgUrl || picUrl(data.boxlotDotplots[0].imgUrl)" alt="" srcset="" v-if="imgUrl || (data.boxlotDotplots && data.boxlotDotplots.length)" /> -->
		<div class="bottom">
			<div class="left mr20" v-if="imgUrl">
				<div class="title">Boxplot</div>
				<div class="images">
					<img :src="imgUrl[0]" />
				</div>
			</div>
			<div class="right" v-if="imgUrl">
				<div class="title">Dotplot</div>
				<div class="images">
					<img :src="imgUrl[1]" />
				</div>
			</div>
		</div>
	</div>
</template>

<style lang="scss" scoped>
@import url("@/styles/content.scss");

.tips {
	color: $themeColor;
	line-height: 3;
}
.tips_wrap {
	margin-top: -20px;
}
.bottom {
	display: flex;
	margin-top: 40px;
	color: $fontColor1;

	.left,
	.right {
		// flex: 1;
		width: 50%;
		display: flex;
		justify-content: center;
		flex-direction: column;
		.title {
			display: flex;
			justify-content: center;
			align-items: center;
			margin-bottom: 20px;
			font-size: $fontSize20;
			font-weight: bold;
		}
		.images {
			width: 100%;
			display: flex;
			flex-direction: column;
			gap: 20px;
		}
	}
}
</style>
