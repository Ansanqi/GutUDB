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
	} = await api.get("/web/histone/page", { ...pagination, ...search.value, ...order })
	pagination.total = parseInt(total)
	tableData.value = records
}

const down = async (exportCurrent, exportSize) => {
	await api.down("/web/histone/exportExcel", { ...pagination, ...search.value, exportCurrent, exportSize })
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
			<el-table :data="tableData" border stripe size="default" @sort-change="sortChange">
				<el-table-column type="index" label="#" width="55" />
				<el-table-column sortable="custom" show-overflow-tooltip prop="histone" label="Histone" min-width="100" />
				<el-table-column sortable="custom" show-overflow-tooltip prop="sapiens" label="Species" min-width="110" />
				<el-table-column sortable="custom" show-overflow-tooltip prop="cellLineTissue" label="Cell line/tissue" min-width="150" />
				<el-table-column sortable="custom" show-overflow-tooltip prop="chromosome" label="Chromosome" min-width="140" />
				<el-table-column sortable="custom" show-overflow-tooltip prop="nameOfPeak" label="Name of peak" min-width="150" />
				<el-table-column sortable="custom" show-overflow-tooltip prop="startPositionOfPeak" label="Start position of peak" min-width="200" />
				<el-table-column sortable="custom" show-overflow-tooltip prop="endPositionOfPeak" label="End position of peak" min-width="190" />
				<el-table-column sortable="custom" show-overflow-tooltip prop="widthOfThePeak" label="Width of the peak" min-width="180" />
				<el-table-column sortable="custom" show-overflow-tooltip prop="peakScore" label="Peak score" min-width="140" />
				<el-table-column sortable="custom" show-overflow-tooltip prop="signalValueOfThePeak" label="Signal value of the peak" min-width="240">
					<template #default="scope">{{ scope.row.signalValueOfThePeak.toFixed(4) }}</template>
				</el-table-column>
				<el-table-column sortable="custom" show-overflow-tooltip prop="strandOfTheGene" label="Strand of the gene" min-width="280" />
				<el-table-column sortable="custom" show-overflow-tooltip prop="log10QValue" label="-log10(q-value)" min-width="160">
					<template #default="scope">{{ scope.row.log10QValue.toFixed(4) }}</template>
				</el-table-column>
				<el-table-column sortable="custom" show-overflow-tooltip prop="log10PValue" label="-log10(p-value)" min-width="160">
					<template #default="scope">
						<span class="c" :style="{ '--color': scope.row.color }">{{ scope.row.log10PValue.toFixed(4) }}</span>
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
