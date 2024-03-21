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
	} = await api.get("/web/apaPametaMerge/page", { ...pagination, ...search.value, ...order })
	pagination.total = parseInt(total)
	tableData.value = records
}

const down = async (exportCurrent, exportSize) => {
	await api.down("/web/apaPametaMerge/exportExcel", { ...pagination,...search.value, exportCurrent, exportSize })
}
const sortChange = async ({ column, prop, order }) => {
	getList({ orderBy: `${prop}_${order}` })
}
</script>

<template>
	<div class="content">
		<Title />
		<Search :search="search" :fn="getList" :down="down" :downPage="pagination.total"/>
		<div class="my-table">
			<el-table :data="tableData" border stripe size="default" @sort-change="sortChange">
				<el-table-column type="index" label="#" width="55" />
				<el-table-column sortable="custom" show-overflow-tooltip prop="ensembleId" label="Ensemble ID" minWidth="180" />
				<el-table-column sortable="custom" show-overflow-tooltip prop="organism" label="Organism" minWidth="160" />
				<el-table-column sortable="custom" show-overflow-tooltip prop="tissue" label="Tissue" minWidth="120" />
				<el-table-column sortable="custom" show-overflow-tooltip prop="conditions" label="Condition" minWidth="330" />
				<el-table-column sortable="custom" show-overflow-tooltip prop="protocol" label="Protocol" minWidth="120" />
				<el-table-column sortable="custom" show-overflow-tooltip prop="chr" label="chr" minWidth="90" />
				<el-table-column sortable="custom" show-overflow-tooltip prop="start" label="start" minWidth="90" />
				<el-table-column sortable="custom" show-overflow-tooltip prop="end" label="end" minWidth="90" />
				<el-table-column sortable="custom" show-overflow-tooltip prop="strand" label="strand" minWidth="100" />
				<el-table-column sortable="custom" show-overflow-tooltip prop="paId" label="PA Id" minWidth="100" />
				<el-table-column sortable="custom" show-overflow-tooltip prop="project" label="Project" minWidth="100" />
				<el-table-column sortable="custom" show-overflow-tooltip prop="sampleId" label="Sample ID" minWidth="120" />
			</el-table>
			<Pagination v-model:page="pagination.current" v-model:size="pagination.size" :total="pagination.total" @page-change="getList()" />
		</div>
	</div>
</template>

<style lang="scss" scoped>
@import url("@/styles/content.scss");
</style>
