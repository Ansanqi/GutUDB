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
	} = await api.get("/web/geoInfo/page", { ...pagination, ...search.value, dataAccessId1: search1.value, ...order })
	pagination.total = parseInt(total)
	tableData.value = records
}

const down = async () => {
	await api.down("/web/geoInfo/exportExcel", { ...pagination,...search.value })
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
		</div> -->
		<!-- <div class="df"> -->
			<Search :search="search" :fn="getList" :down="down"/>
		<!-- </div> -->
		<div class="my-table">
			<el-table :data="tableData" border stripe size="default" @sort-change="sortChange">
				<el-table-column type="index" label="#" width="55" />
				<el-table-column sortable="custom" show-overflow-tooltip prop="dataAccessId" label="Data Access ID" min-width="140">
					<template #default="scope">
						<router-link
							v-if="scope.row.fileNo != '-'"
							:to="{ path: '/omics/transcriptomics/alternative-splicing/data-access', query: { dataAccessId: scope.row.dataAccessId, file: scope.row.fileNo } }"
							class="table-link"
							>{{ scope.row.dataAccessId }}</router-link
						>
						<span v-else>{{ scope.row.dataAccessId }}</span>
					</template>
				</el-table-column>
				<el-table-column sortable="custom" show-overflow-tooltip prop="groups" label="Group" min-width="100" />
				<el-table-column sortable="custom" show-overflow-tooltip prop="fileNo" label="File" min-width="80" />
				<el-table-column sortable="custom" show-overflow-tooltip prop="run" label="Run" min-width="110" />
				<el-table-column sortable="custom" show-overflow-tooltip prop="information" label="Information" min-width="180" />
				<el-table-column sortable="custom" show-overflow-tooltip prop="conditions" label="Condition" min-width="200" />
				<el-table-column sortable="custom" show-overflow-tooltip prop="cellLineTissueOrganiod" label="Cell line/Tissue/Organiod" min-width="220" />
				<el-table-column sortable="custom" show-overflow-tooltip prop="tissue" label="Tissue" min-width="180" />
				<el-table-column sortable="custom" show-overflow-tooltip prop="typeCategory" label="Type_category" min-width="140" />
				<el-table-column sortable="custom" show-overflow-tooltip prop="cellType" label="Cell Type" min-width="110" />
				<el-table-column sortable="custom" show-overflow-tooltip prop="bodyTite" label="Body site" min-width="110" />
			</el-table>
			<Pagination v-model:page="pagination.current" v-model:size="pagination.size" :total="pagination.total" @page-change="getList()"></Pagination>
		</div>
	</div>
</template>

<style lang="scss" scoped>
@import url("@/styles/content.scss");
</style>
