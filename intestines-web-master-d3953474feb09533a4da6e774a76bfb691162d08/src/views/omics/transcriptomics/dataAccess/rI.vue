<script setup>
const route = useRoute()
const { dataAccessId, file } = route.query
const search = ref({})
const tableData = ref([])

// 分页
const pagination = reactive({
	current: 1,
	size: 10,
	total: 0,
})

const incLevel1Arr = ref([])
const incLevel2Arr = ref([])

getList()
async function getList(order) {
	const {
		data: { total, records },
	} = await api.get("/web/riMatsJcec/page", { ...pagination, ...search.value, dataAccessId, file })
	pagination.total = parseInt(total)
	if (records?.length > 0) {
		incLevel1Arr.value = records[0]?.incLevel1Name?.split(",")
		incLevel2Arr.value = records[0]?.incLevel2Name?.split(",")
	}
	tableData.value = records
}

const down = async () => {
	await api.down("/web/riMatsJcec/exportExcel", { ...pagination, ...search.value, dataAccessId, file })
}
const sortChange = async ({ column, prop, order }) => {
	getList({ orderBy: `${prop}_${order}` })
}
</script>

<template>
	<div class="sub_content">
		<Search :search="search" :fn="getList" :down="down" title="rI"/>
		<div class="my-table">
			<el-table :data="tableData" border stripe size="default" @sort-change="sortChange">
				<el-table-column type="index" label="#" width="55" />
				<el-table-column sortable="custom" show-overflow-tooltip prop="ensembleId" label="Ensemble ID" min-width="180" />
				<el-table-column sortable="custom" show-overflow-tooltip prop="geneName" label="Gene Name" min-width="140">
					<template #default="scope">
						<span v-if="scope.row.geneName === 'NA' || !scope.row.geneName">{{ scope.row.geneName }}</span>
						<router-link v-else :to="{ path: '/gene/index', query: { geneName: scope.row.geneName } }" class="table-link">{{ scope.row.geneName }}</router-link>
					</template>
				</el-table-column>
				<el-table-column sortable="custom" show-overflow-tooltip prop="chr" label="chr" min-width="120" />
				<el-table-column sortable="custom" show-overflow-tooltip prop="strand" label="strand" min-width="120" />
				<el-table-column sortable="custom" show-overflow-tooltip prop="riExonStart0Base" label="riExonStart_0base" min-width="200" />
				<el-table-column sortable="custom" show-overflow-tooltip prop="riExonEnd" label="riExonEnd" min-width="140" />
				<el-table-column sortable="custom" show-overflow-tooltip prop="upstreamEs" label="upstreamES" min-width="130" />
				<el-table-column sortable="custom" show-overflow-tooltip prop="upstreamEe" label="upstreamEE" min-width="140" />
				<el-table-column sortable="custom" show-overflow-tooltip prop="downstreamEs" label="downstreamES" min-width="150" />
				<el-table-column sortable="custom" show-overflow-tooltip prop="downstreamEe" label="downstreamEE" min-width="150" />
				<el-table-column sortable="custom" show-overflow-tooltip prop="id" label="ID" min-width="120" />
				<el-table-column sortable="custom" show-overflow-tooltip prop="ijcSample1" label="IJC_SAMPLE_1" min-width="150" />
				<el-table-column sortable="custom" show-overflow-tooltip prop="sjcSample1" label="SJC_SAMPLE_1" min-width="150" />
				<el-table-column sortable="custom" show-overflow-tooltip prop="ijcSample2" label="IJC_SAMPLE_2" min-width="150" />
				<el-table-column sortable="custom" show-overflow-tooltip prop="sjcSample2" label="SJC_SAMPLE_2" min-width="150" />
				<el-table-column sortable="custom" show-overflow-tooltip prop="incFormLen" label="IncFormLen" min-width="140" />
				<el-table-column sortable="custom" show-overflow-tooltip prop="skipFormLen" label="SkipFormLen" min-width="140" />
				<el-table-column sortable="custom" show-overflow-tooltip prop="pvalue" label="PValue" min-width="120">
					<template #default="scope">
						{{ parseFloat(scope.row.pvalue).toFixed(4) }}
					</template>
				</el-table-column>
				<el-table-column sortable="custom" show-overflow-tooltip prop="fdr" label="FDR" min-width="120" />
				<el-table-column sortable="custom" show-overflow-tooltip prop="incLevel1" label="IncLevel1" min-width="210">
					<template #header="scope">
						IncLevel1
						<el-popover :width="200">
							<template #reference>
								<el-icon size="16"><EpQuestionFilled /></el-icon>
							</template>
							<template #default>
								<div v-for="item in incLevel1Arr" class="df fdc gap20" style="margin: 0">{{ item }}</div>
							</template>
						</el-popover>
					</template>
					<template #default="scope">
						{{ scope.row.incLevel1 }}
					</template>
				</el-table-column>
				<el-table-column sortable="custom" show-overflow-tooltip prop="incLevel2" label="IncLevel2" min-width="150">
					<template #header="scope">
						IncLevel2
						<el-popover :width="200">
							<template #reference>
								<el-icon size="16"><EpQuestionFilled /></el-icon>
							</template>
							<template #default>
								<div v-for="item in incLevel2Arr" class="df fdc gap20" style="margin: 0">{{ item }}</div>
							</template>
						</el-popover>
					</template>
					<template #default="scope">
						{{ scope.row.incLevel2 }}
					</template>
				</el-table-column>
				<el-table-column sortable="custom" show-overflow-tooltip prop="incLevelDifference" label="IncLevelDifference" min-width="180" />
			</el-table>
			<Pagination v-model:page="pagination.current" v-model:size="pagination.size" :total="pagination.total" @page-change="getList()"></Pagination>
		</div>
	</div>
</template>

<style lang="scss" scoped>
@import url("@/styles/content.scss");
</style>
