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
	} = await api.get("/web/alternativeSplicing/page", { ...pagination, ...search.value, ...order })
	pagination.total = parseInt(total)
	tableData.value = records
}

const down = async () => {
	await api.down("/web/alternativeSplicing/exportExcel", { ...pagination,...search.value })
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
			<el-table :data="tableData" border  size="default" @sort-change="sortChange">
				<el-table-column type="index" label="#" width="55" />
				<el-table-column sortable="custom" show-overflow-tooltip prop="geneName" label="Gene Name" minWidth="110">
					<template #default="scope">
						<router-link :to="{ path: '/gene/index', query: { geneName: scope.row.geneName } }" class="table-link">{{ scope.row.geneName }}</router-link>
					</template>
				</el-table-column>
				<el-table-column sortable="custom" show-overflow-tooltip prop="cluster1" label="Cluster1" minWidth="140" />
				<el-table-column sortable="custom" show-overflow-tooltip prop="cluster2" label="Cluster2" minWidth="130" />
				<el-table-column sortable="custom" show-overflow-tooltip prop="junction" label="Junction" minWidth="230" />
				<el-table-column sortable="custom" show-overflow-tooltip prop="logFc" label="logFC" minWidth="170" />
				<el-table-column sortable="custom" show-overflow-tooltip prop="adjPVal" label="adj.P.Val" minWidth="190" />
			</el-table>
			<Pagination v-model:page="pagination.current" v-model:size="pagination.size" :total="pagination.total" @page-change="getList()" />
		</div>
	</div>
</template>

<style lang="scss" scoped>
@import url("@/styles/content.scss");
</style>
