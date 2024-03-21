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
	} = await api.get("/web/species/page", { ...pagination, ...search.value, ...order })
	pagination.total = parseInt(total)
	tableData.value = records
}

const down = async () => {
	await api.down("/web/species/exportExcel", { ...pagination, ...search.value })
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
				<el-table-column sortable="custom" show-overflow-tooltip prop="bodySite" label="Body site" minWidth="130" />
				<el-table-column sortable="custom" show-overflow-tooltip prop="disease" label="Disease" minWidth="120" />
				<el-table-column sortable="custom" show-overflow-tooltip prop="speciesName" label="Species name" minWidth="160" />
				<el-table-column sortable="custom" show-overflow-tooltip prop="ifGmrepo" label="ifGmrepo" minWidth="130" />
				<el-table-column sortable="custom" show-overflow-tooltip prop="ifHmdad" label="ifHmdad" minWidth="120">
					<template #default="scope">{{ scope.row.ifHmdad || "NA" }}</template>
				</el-table-column>
				<el-table-column sortable="custom" show-overflow-tooltip prop="ifMvp" label="ifMvp" minWidth="90">
					<template #default="scope">{{ scope.row.ifMvp || "NA" }}</template>
				</el-table-column>
				<el-table-column sortable="custom" show-overflow-tooltip prop="loadedUidNum" label="loaded_uid_num" minWidth="170" />
				<el-table-column sortable="custom" show-overflow-tooltip prop="mvpData" label="mvpData" minWidth="120">
					<template #default="scope">{{ scope.row.mvpData || "NA" }}</template>
				</el-table-column>
				<el-table-column sortable="custom" show-overflow-tooltip prop="ncbiTaxonId" label="ncbi_taxon_id" minWidth="150" />
				<el-table-column sortable="custom" show-overflow-tooltip prop="relativeAbundanceAvg" label="relative_abundance_avg" minWidth="220">
					<template #default="scope">{{ parseFloat(scope.row.relativeAbundanceAvg).toFixed(4) }}</template>
				</el-table-column>
				<el-table-column sortable="custom" show-overflow-tooltip prop="relativeAbundanceMed" label="relative_abundance_med" minWidth="220">
					<template #default="scope">{{ parseFloat(scope.row.relativeAbundanceMed).toFixed(4) }}</template>
				</el-table-column>
				<el-table-column sortable="custom" show-overflow-tooltip prop="relativeAbundanceStd" label="relative_abundance_std" minWidth="220">
					<template #default="scope">{{ parseFloat(scope.row.relativeAbundanceStd).toFixed(4) }}</template>
				</el-table-column>
				<el-table-column sortable="custom" show-overflow-tooltip prop="relativeAbundanceSum" label="relative_abundance_sum" minWidth="220">
					<template #default="scope">{{ parseFloat(scope.row.relativeAbundanceSum).toFixed(4) }}</template>
				</el-table-column>
			</el-table>
			<Pagination v-model:page="pagination.current" v-model:size="pagination.size" :total="pagination.total" @page-change="getList()" />
		</div>
	</div>
</template>

<style lang="scss" scoped>
@import url("@/styles/content.scss");
</style>
