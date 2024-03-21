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
	} = await api.get("/web/snp/page", { ...pagination, ...search.value, ...order })
	pagination.total = parseInt(total)
	tableData.value = records
}

const down = async () => {
	await api.down("/web/snp/exportExcel", { ...pagination,...search.value })
}
const sortChange = async ({ column, prop, order }) => {
	getList({ orderBy: `${prop}_${order}` })
}
</script>

<template>
	<div class="subContent">
		<Search :search="search" :fn="getList" :down="down" title="snp"/>
		<div class="my-table">
			<el-table :data="tableData" border stripe size="default" @sort-change="sortChange">
				<el-table-column type="index" label="#" width="55" />
				<el-table-column sortable="custom" show-overflow-tooltip prop="geneName" label="Gene Name" min-width="130">
					<template #default="scope">
						<span v-if="scope.row.geneName === 'NA' || !scope.row.geneName">{{ scope.row.geneName }}</span>
						<!-- <router-link v-else :to="{ path: '/gene/index', query: { geneName: scope.row.geneName } }" class="table-link">{{ scope.row.geneName }}</router-link> -->
						<template v-else v-for="(v, index) in scope.row.geneName.split('|')">
							<router-link :to="{ path: '/gene/index', query: { geneName: v } }" class="table-link">{{ v }}</router-link>{{ index === scope.row.geneName.split("|").length - 1 ? " " : "," }}
						</template>
					</template>
				</el-table-column>
				<el-table-column sortable="custom" show-overflow-tooltip prop="disease" label="Disease" min-width="220" />
				<el-table-column sortable="custom" show-overflow-tooltip prop="name" label="Name" min-width="240" />
				<el-table-column sortable="custom" show-overflow-tooltip prop="grch38Chromosome" label="GRCh38Chromosome" min-width="190" />
				<el-table-column sortable="custom" show-overflow-tooltip prop="grch38Location" label="GRCh38 Location" min-width="180" />
				<el-table-column sortable="custom" show-overflow-tooltip prop="condition" label="Condition" min-width="222">
					<template #default="scope">{{ scope.row.conditions || 'NA' }}</template>
				</el-table-column>
				<el-table-column sortable="custom" show-overflow-tooltip prop="clinicalSignificance" label="Clinical Significance" min-width="220" />
				<el-table-column sortable="custom" show-overflow-tooltip prop="reviewStatus" label="Review Status" min-width="160" />
				<el-table-column sortable="custom" show-overflow-tooltip prop="proteinChange" label="Protein Change" min-width="170" />
				<el-table-column sortable="custom" show-overflow-tooltip prop="accession" label="Accession" min-width="140" />
			</el-table>
			<Pagination v-model:page="pagination.current" v-model:size="pagination.size" :total="pagination.total" @page-change="getList()"></Pagination>
		</div>
	</div>
</template>

<style lang="scss" scoped>
@import url("@/styles/content.scss");
</style>
