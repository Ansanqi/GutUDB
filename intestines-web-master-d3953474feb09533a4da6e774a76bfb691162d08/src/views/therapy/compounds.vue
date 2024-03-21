<script setup>
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
	} = await api.get("/web/chemicalCompounds/page", { ...pagination, ...search.value, ...order })
	pagination.total = parseInt(total)
	tableData.value = records
}

const down = async () => {
	await api.down("/web/chemicalCompounds/exportExcel", { ...pagination,...search.value })
}
const sortChange = async ({ column, prop, order }) => {
	getList({ orderBy: `${prop}_${order}` })
}
</script>

<template>
	<div class="content">
		<Title />
		<Search :search="search" :fn="getList" :down="down"/>
		<div class="my-table">
			<el-table :data="tableData" border stripe size="default" @sort-change="sortChange">
				<el-table-column type="index" label="#" width="55" />
				<el-table-column sortable="custom" show-overflow-tooltip prop="diseaseRelatedGenes" label="Disease related genes" minWidth="200">
					<template #default="scope">
						<router-link :to="{ path: '/gene/index', query: { geneName: scope.row.diseaseRelatedGenes } }" class="table-link">{{ scope.row.diseaseRelatedGenes }}</router-link>
					</template>
				</el-table-column>
				<el-table-column sortable="custom" show-overflow-tooltip prop="disease" label="Disease" minWidth="200" />
				<el-table-column sortable="custom" show-overflow-tooltip prop="species" label="Species" minWidth="100" />
				<el-table-column sortable="custom" show-overflow-tooltip prop="directEvidence" label="Direct Evidence" minWidth="160" />
				<el-table-column sortable="custom" show-overflow-tooltip prop="inferenceNetwork" label="Inference Network" minWidth="1000" />
				<el-table-column sortable="custom" show-overflow-tooltip prop="source" label="Source" minWidth="110">
					<template #default="scope">
						<a class="table-link" :href="scope.row.sourceUrl" target="_blank">{{ scope.row.source }}</a>
					</template>
				</el-table-column>
				<el-table-column sortable="custom" show-overflow-tooltip prop="referenceCount" label="Reference Count" minWidth="160" />
			</el-table>
			<Pagination v-model:page="pagination.current" v-model:size="pagination.size" :total="pagination.total" @page-change="getList()" />
		</div>
	</div>
</template>

<style lang="scss" scoped>
@import url("@/styles/content.scss");
</style>
