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
	} = await api.get("/web/metabolomics/page", {...pagination, ...search.value, ...order})
	pagination.total = parseInt(total)
	tableData.value = records
}

const down = async () => {
	await api.down("/web/metabolomics/exportExcel", { ...pagination, ...search.value })
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
				<el-table-column sortable="custom" show-overflow-tooltip prop="geneName" label="Gene Name" minWidth="140">
					<template #default="scope">
						<span v-if="scope.row.geneName === 'NA' || !scope.row.geneName">{{ scope.row.geneName }}</span>
						<router-link v-else :to="{ path: '/gene/index', query: { geneName: scope.row.geneName } }" class="table-link">{{ scope.row.geneName }}</router-link>
					</template>
				</el-table-column>
				<el-table-column sortable="custom" show-overflow-tooltip prop="disease" label="Disease" minWidth="160" />
				<el-table-column sortable="custom" show-overflow-tooltip prop="gutMicrobiota" label="Gut Microbiota" minWidth="230" />
				<el-table-column sortable="custom" show-overflow-tooltip prop="metabolite" label="Metabolite" minWidth="170" />
				<el-table-column sortable="custom" show-overflow-tooltip prop="species" label="Species" minWidth="110" />
				<el-table-column sortable="custom" show-overflow-tooltip prop="classification" label="Classification" minWidth="140" />
				<el-table-column sortable="custom" show-overflow-tooltip prop="description" label="Description" minWidth="140" />
				<el-table-column sortable="custom" show-overflow-tooltip prop="sampleType" label="Sample Type" minWidth="170" />
				<el-table-column sortable="custom" show-overflow-tooltip prop="substrate" label="Substrate" minWidth="130" />
				<el-table-column sortable="custom" show-overflow-tooltip prop="alteration" label="Alteration" minWidth="130" />
				<el-table-column sortable="custom" show-overflow-tooltip prop="experimentalMethod" label="Experimental Method" minWidth="200" />
				<el-table-column sortable="custom" show-overflow-tooltip prop="measurementTechnique" label="Measurement Technique" minWidth="270" />
				<el-table-column sortable="custom" show-overflow-tooltip prop="geneAssociation" label="Gene Association" minWidth="370">
					<template #default="scope">
						<span v-if="scope.row.geneAssociation === 'NA' || !scope.row.geneAssociation">NA</span>
						<a v-else class="table-link" :href="scope.row.geneAssociation" target="_blank">{{ scope.row.geneAssociation }}</a>
					</template>
				</el-table-column>
				<el-table-column sortable="custom" show-overflow-tooltip prop="pmidAssociation" label="PMID Association" minWidth="300">
					<template #default="scope">
						<span v-if="scope.row.pmidAssociation === 'NA' || !scope.row.pmidAssociation">NA</span>
						<a v-else class="table-link" :href="scope.row.pmidAssociation" target="_blank">{{ scope.row.pmidAssociation }}</a>
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
