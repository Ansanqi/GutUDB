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
	} = await api.get("/web/homoSapiensNgsm6a/page", { ...pagination, ...search.value, ...order })
	pagination.total = parseInt(total)
	tableData.value = records
}

const down = async () => {
	await api.down("/web/homoSapiensNgsm6a/exportExcel", { ...pagination,...search.value })
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
				<el-table-column sortable="custom" show-overflow-tooltip prop="seqnames" label="Seqnames" min-width="120" />
				<el-table-column sortable="custom" show-overflow-tooltip prop="modification" label="Modification" min-width="140" />
				<el-table-column sortable="custom" show-overflow-tooltip prop="cell" label="Cell" min-width="200" />
				<el-table-column sortable="custom" show-overflow-tooltip prop="conditions" label="Condition" min-width="200" />
				<el-table-column sortable="custom" show-overflow-tooltip prop="start" label="Start" min-width="120" />
				<el-table-column sortable="custom" show-overflow-tooltip prop="end" label="End" min-width="120" />
				<el-table-column sortable="custom" show-overflow-tooltip prop="width" label="Width" min-width="120" />
				<el-table-column sortable="custom" show-overflow-tooltip prop="strand" label="Strand" min-width="120" />
				<el-table-column sortable="custom" show-overflow-tooltip prop="technique" label="Technique" min-width="120" />
				<el-table-column sortable="custom" show-overflow-tooltip prop="resolution" label="Resolution" min-width="120" />
				<el-table-column sortable="custom" show-overflow-tooltip prop="project" label="Project" min-width="120" />
			</el-table>
			<Pagination v-model:page="pagination.current" v-model:size="pagination.size" :total="pagination.total" @page-change="getList()"></Pagination>
		</div>
	</div>
</template>

<style lang="scss" scoped>
@import url("@/styles/content.scss");
</style>
