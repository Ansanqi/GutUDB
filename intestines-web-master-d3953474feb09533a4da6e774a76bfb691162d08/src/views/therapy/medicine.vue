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
	} = await api.get("/web/chineseMedicine/page", { ...pagination, ...search.value, ...order })
	pagination.total = parseInt(total)
	tableData.value = records
}

const down = async () => {
	await api.down("/web/chineseMedicine/exportExcel", { ...pagination,...search.value })
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
				<el-table-column sortable="custom" show-overflow-tooltip prop="disease" label="Disease" minWidth="230" />
				<el-table-column sortable="custom" show-overflow-tooltip prop="diseaseRelatedGenes" label="Disease related genes" minWidth="600" />
				<el-table-column sortable="custom" show-overflow-tooltip prop="herbsAssociatedWithThisDisease" label="Herbs Associated with This Disease" minWidth="230" >
					<template #default="scope">
						<a class="table-link" :href="scope.row.herbsAssociatedWithThisDiseaseUrl" target="_blank">{{ scope.row.herbsAssociatedWithThisDisease }}</a>
					</template>
					</el-table-column>
			</el-table>
			<Pagination v-model:page="pagination.current" v-model:size="pagination.size" :total="pagination.total" @page-change="getList()" />
		</div>
	</div>
</template>

<style lang="scss" scoped>
@import url("@/styles/content.scss");
</style>
