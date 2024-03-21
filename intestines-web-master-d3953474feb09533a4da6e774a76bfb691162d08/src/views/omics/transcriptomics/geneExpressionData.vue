<script setup>
const search = ref({})
const search1 = ref("")
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
	} = await api.get("/web/geoInformation/page", { ...pagination, ...search.value, project1: search1.value, ...order })
	pagination.total = parseInt(total)
	tableData.value = records
}

const down = async () => {
	await api.down("/web/geoInformation/exportExcel", { ...pagination,...search.value })
}
const sortChange = async ({ column, prop, order }) => {
	getList({ orderBy: `${prop}_${order}` })
}
</script>

<template>
	<div class="content">
		<Title />
		<!-- <div class="my-tsv" @click="down">
			<span class="iconfont icon-xiazai"></span>
			<span>CSV</span>
		</div>
		<div class="df"> -->
			<Search :search="search" :fn="getList" :down="down" />
		<!-- </div> -->
		<div class="my-table">
			<el-table :data="tableData" border stripe size="default" @sort-change="sortChange">
				<el-table-column type="index" label="#" width="55" />
				<el-table-column sortable="custom" show-overflow-tooltip prop="project" label="Project" min-width="120">
					<template #default="scope">
						<router-link :to="{ path: '/omics/project-detail', query: { projectNo: scope.row.project } }" class="table-link">{{ scope.row.project }}</router-link>
					</template>
				</el-table-column>
				<el-table-column sortable="custom" show-overflow-tooltip prop="disease" label="Disease" min-width="150" />
				<el-table-column sortable="custom" show-overflow-tooltip prop="species" label="Species" min-width="120" />
				<el-table-column sortable="custom" show-overflow-tooltip prop="tissueCellLine" label="Tissue/Cell line" min-width="140" />
				<el-table-column sortable="custom" show-overflow-tooltip prop="cases" label="CASE" min-width="280" />
				<el-table-column sortable="custom" show-overflow-tooltip prop="control" label="Control" min-width="200" />
				<el-table-column sortable="custom" prop="pmid" label="PMID" minWidth="100">
					<template #default="scope">
						<span v-if="scope.row.pmid === 'NA' || !scope.row.pmid">NA</span>
						<template v-else v-for="(v, index) in scope.row.pmid.split(',')">
							<a class="table-link" :href="scope.row.pmidUrl.split(',')[index]" target="_blank">{{ v }}</a
							>{{ index === scope.row.pmid.split(",").length - 1 ? " " : "," }}
						</template>
					</template>
				</el-table-column>
				<el-table-column sortable="custom" show-overflow-tooltip prop="geoAssociation" label="GEO Association" min-width="410">
					<template #default="scope">
						<a class="table-link" :href="scope.row.geoAssociation" target="_blank">{{ scope.row.geoAssociation }}</a>
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
