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
	} = await api.get("/web/chromosomeStructure/page", { ...pagination, ...search.value, ...order })
	pagination.total = parseInt(total)
	tableData.value = records
}

const down = async () => {
	await api.down("/web/chromosomeStructure/exportExcel", { ...pagination,...search.value })
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
				<el-table-column sortable="custom" show-overflow-tooltip prop="fileName" label="File Name" min-width="210" />
				<el-table-column sortable="custom" show-overflow-tooltip prop="hiCDatasetTitle" label="Hi-C Dataset Title" min-width="210" />
				<el-table-column sortable="custom" show-overflow-tooltip prop="structure3d" label="3D Structure" min-width="150">
					<template #default="scope">
						<a class="table-link" :href="scope.row.structure3dUrl" target="_blank">{{ scope.row.structure3d }}</a>
					</template>
				</el-table-column>
				<el-table-column sortable="custom" show-overflow-tooltip prop="organism" label="Organism" min-width="150" />
				<el-table-column sortable="custom" show-overflow-tooltip prop="gsdbId" label="GSDB ID" min-width="120" />
				<el-table-column sortable="custom" show-overflow-tooltip prop="project" label="Project" min-width="120">
					<template #default="scope">
						<a class="table-link" :href="scope.row.projectUrl" target="_blank">{{ scope.row.project }}</a>
					</template>
				</el-table-column>
			</el-table>
			<Pagination v-model:page="pagination.current" v-model:size="pagination.size" :total="pagination.total" @page-change="getList()"></Pagination>
		</div>
	</div>
</template>

<style lang="scss" scoped>
@import url("@/styles/content.scss");
</style>
