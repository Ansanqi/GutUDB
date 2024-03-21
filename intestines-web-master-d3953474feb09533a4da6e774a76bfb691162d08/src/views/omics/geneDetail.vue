<script setup>
import { useDownload } from "@/hooks/useDownload"
const route = useRoute()
const search = ref({})
const tableData = ref([])

// 分页
const pagination = reactive({
	current: 1,
	size: 10,
	total: 0,
})

getList()
async function getList(order) {
	const {
		data: { total, records },
	} = await api.get("/web/singleCellGeneExpressData/page", { ...pagination, ...search.value, uniqueId: route.query.id, ...order })
	pagination.total = parseInt(total)
	tableData.value = records
	imgUrl.value = records[0]?.imgUrl ? picUrl(records[0].imgUrl) : ""
}

const down = async () => {
	await api.down("/web/singleCellGeneExpressData/exportExcel", { ...pagination, ...search.value, uniqueId: route.query.id })
}

const imgUrl = ref()
const cellMouseEnter = (row) => {
	imgUrl.value = picUrl(row.imgUrl)
}

const sortChange = async ({ column, prop, order }) => {
	getList({ orderBy: `${prop}_${order}` })
}
</script>

<template>
	<el-row>
		<el-col :span="16">
			<div class="content">
				<Title :is-back="true" />
				<Search :search="search" :fn="getList" :down="down" />
				<div class="my-table">
					<el-table :data="tableData" border size="default" @cell-mouse-enter="cellMouseEnter" @sort-change="sortChange">
						<el-table-column type="index" label="#" width="55" />
						<el-table-column sortable="custom" show-overflow-tooltip prop="ensembleId" label="Ensemble ID" minWidth="140" />
						<el-table-column sortable="custom" show-overflow-tooltip prop="geneName" label="Gene Name" minWidth="130">
							<template #default="scope">
								<span v-if="scope.row.geneName === 'NA' || !scope.row.geneName">{{ scope.row.geneName }}</span>
								<router-link v-else :to="{ path: '/gene/index', query: { geneName: scope.row.geneName } }" class="table-link">{{ scope.row.geneName }}</router-link>
							</template>
						</el-table-column>
						<el-table-column sortable="custom" show-overflow-tooltip prop="name" label="Name" minWidth="250" />
						<el-table-column sortable="custom" show-overflow-tooltip prop="cancerType" label="Cancer Type" minWidth="240" />
						<el-table-column sortable="custom" show-overflow-tooltip prop="tissue" label="Tissue" minWidth="110" />
						<el-table-column sortable="custom" show-overflow-tooltip prop="treatmentType" label="Treatment Type" minWidth="160" />
						<el-table-column sortable="custom" show-overflow-tooltip prop="uniqueId" label="Unique Id" minWidth="200" />
						<el-table-column sortable="custom" show-overflow-tooltip prop="cluster" label="Cluster" minWidth="100" />
						<el-table-column sortable="custom" show-overflow-tooltip prop="enrichmentCellTypes" label="Enrichment Cell Types" minWidth="200" />
						<el-table-column sortable="custom" show-overflow-tooltip prop="cellAmount" label="Cell amount" minWidth="130" />
						<el-table-column sortable="custom" show-overflow-tooltip prop="pval" label="p_val" minWidth="90" />
						<el-table-column sortable="custom" show-overflow-tooltip prop="avgLog2Fc" label="avg_log2FC" minWidth="130">
							<template #default="scope">
								<span class="c" :style="{ '--color': scope.row.color }">{{ parseFloat(scope.row.avgLog2Fc).toFixed(4) }}</span>
							</template>
						</el-table-column>
						<el-table-column sortable="custom" show-overflow-tooltip prop="pct1" label="pct.1" minWidth="90">
							<template #default="scope">
								{{ scope.row.pct1.toFixed(4) }}
							</template>
						</el-table-column>
						<el-table-column sortable="custom" show-overflow-tooltip prop="pct2" label="pct.2" minWidth="90">
							<template #default="scope">
								{{ scope.row.pct2.toFixed(4) }}
							</template>
						</el-table-column>
						<el-table-column sortable="custom" show-overflow-tooltip prop="pvalAdj" label="p_val_adj" minWidth="120" />
						<el-table-column sortable="custom" show-overflow-tooltip prop="title" label="Title" minWidth="500" />
					</el-table>
					<Pagination v-model:page="pagination.current" v-model:size="pagination.size" :total="pagination.total" @page-change="getList()" />
				</div>
			</div>
		</el-col>
		<el-col class="img" :span="8">
			<el-image :src="imgUrl">
				<template #error>
					<div class="image-slot">
						<el-empty />
					</div>
				</template>
			</el-image>
			<div v-if="imgUrl" class="el-button" style="margin-top: 25px" @click="useDownload(imgUrl)">
				<span class="iconfont icon-xiazai" style="margin-right: 5px"></span>
				<span>Download</span>
			</div>
		</el-col>
	</el-row>
</template>

<style lang="scss" scoped>
@import url("@/styles/content.scss");
.el-row {
	padding-right: 40px;
	.content {
		margin-right: 20px;
	}
	.img {
		background-color: #fff;
		margin-bottom: 40px;
		display: flex;
		align-items: center;
		justify-content: center;
		flex-direction: column;
		padding: 0 10px;
	}
}
</style>
