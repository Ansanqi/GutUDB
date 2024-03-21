<script setup>
const search = ref({})
const tableData = ref([])

// 分页
const pagination = reactive({
	current: 1,
	size: 10,
	total: 50,
})

getList()
async function getList(order) {
	const {
		data: { total, records },
	} = await api.get("/web/dnaMethylation/page", { ...pagination, ...search.value, ...order })
	pagination.total = parseInt(total)
	tableData.value = records
}

const down = async (exportCurrent, exportSize) => {
	await api.down("/web/dnaMethylation/exportExcel", { ...pagination, ...search.value, exportCurrent, exportSize })
}
const sortChange = async ({ column, prop, order }) => {
	getList({ orderBy: `${prop}_${order}` })
}
</script>

<template>
	<div class="content">
		<Title />
		<Search :search="search" :fn="getList" :down="down" :downPage="pagination.total" />
		<div class="my-table">
			<el-table :data="tableData" border stripe fit size="default" @sort-change="sortChange">
				<el-table-column type="index" label="#" width="55" />
				<el-table-column sortable="custom" show-overflow-tooltip prop="ensembleId" label="Ensemble ID" min-width="180" />
				<el-table-column sortable="custom" show-overflow-tooltip prop="geneName" label="Gene Name" min-width="130">
					<template #default="scope">
						<router-link :to="{ path: '/gene/index', query: { geneName: scope.row.geneName } }" class="table-link">{{ scope.row.geneName }}</router-link>
					</template>
				</el-table-column>
				<el-table-column sortable="custom" show-overflow-tooltip prop="chromosome" label="Chromosome" min-width="140" />
				<el-table-column sortable="custom" show-overflow-tooltip prop="startPosition" label="Start Position" min-width="140" />
				<el-table-column sortable="custom" show-overflow-tooltip prop="endPosition" label="End Position" min-width="140" />
				<el-table-column sortable="custom" show-overflow-tooltip prop="cpgLength" label="CpG Length" min-width="140" />
				<el-table-column sortable="custom" show-overflow-tooltip prop="cpgNumber" label="CpG Number" min-width="140" />
				<el-table-column sortable="custom" show-overflow-tooltip prop="averageMethylationLevel" label="Average Methylation Level" min-width="230">
					<template #default="scope">
						<span class="c" :style="{ '--color': scope.row.color }">{{ scope.row.averageMethylationLevel.toFixed(4) }}</span>
					</template>
				</el-table-column>
				<el-table-column sortable="custom" show-overflow-tooltip prop="strand" label="Strand" min-width="110" />
				<el-table-column sortable="custom" show-overflow-tooltip prop="project" label="Project" min-width="120">
					<template #default="scope">{{ scope.row.project || "NA" }}</template>
				</el-table-column>
				<el-table-column sortable="custom" show-overflow-tooltip prop="sampleId" label="Sample ID" min-width="120" />
				<el-table-column sortable="custom" show-overflow-tooltip prop="species" label="Species" min-width="130" />
				<el-table-column sortable="custom" show-overflow-tooltip prop="genomicsLocation" label="Genomics Location" min-width="180" />
				<el-table-column sortable="custom" show-overflow-tooltip prop="sampleName" label="Sample Name" min-width="230" />
				<el-table-column sortable="custom" show-overflow-tooltip prop="description" label="Description" min-width="230" />
				<el-table-column sortable="custom" show-overflow-tooltip prop="tissueCellLine" label="Tissue/Cell Line" min-width="160" />
				<el-table-column sortable="custom" show-overflow-tooltip prop="gender" label="Gender" min-width="100" />
				<el-table-column sortable="custom" show-overflow-tooltip prop="age" label="Age" min-width="80" />
				<el-table-column sortable="custom" show-overflow-tooltip prop="disease" label="Disease" min-width="140" />
				<el-table-column sortable="custom" show-overflow-tooltip prop="platform" label="Platform" min-width="200" />
			</el-table>
			<Pagination v-model:page="pagination.current" v-model:size="pagination.size" :total="pagination.total" @page-change="getList()"></Pagination>
		</div>
	</div>
</template>

<style lang="scss" scoped>
.el-radio-group {
	align-items: flex-start;
}
@import url("@/styles/content.scss");
</style>
