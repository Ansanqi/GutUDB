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
	} = await api.get("/web/structuraVariantGenes/page", { ...pagination, ...search.value, ...order })
	pagination.total = parseInt(total)
	tableData.value = records
}

const down = async () => {
	await api.down("/web/structuraVariantGenes/exportExcel", { ...pagination,...search.value })
}
const sortChange = async ({ column, prop, order }) => {
	getList({ orderBy: `${prop}_${order}` })
}
</script>

<template>
	<div class="subContent">
		<Search :search="search" :fn="getList" :down="down" title="structuralVariantGenes"/>
		<div class="my-table">
			<el-table :data="tableData" border stripe size="default" @sort-change="sortChange">
				<el-table-column type="index" label="#" width="55" />
				<el-table-column sortable="custom" show-overflow-tooltip prop="geneName" label="Gene Name" min-width="120">
					<template #default="scope">
						<span v-if="scope.row.geneName === 'NA' || !scope.row.geneName">{{ scope.row.geneName }}</span>
						<router-link v-else :to="{ path: '/gene/index', query: { geneName: scope.row.geneName } }" class="table-link">{{ scope.row.geneName }}</router-link>
					</template>
				</el-table-column>
				<el-table-column sortable="custom" show-overflow-tooltip prop="disease" label="Disease" min-width="150" />
				<el-table-column sortable="custom" show-overflow-tooltip prop="isCancerGeneSourceOncoKb" label="Is Cancer Gene (source: OncoKB)" min-width="230" />
				<el-table-column sortable="custom" show-overflow-tooltip prop="freq" label="Freq" min-width="100">
					<template #header>
						Freq
						<el-popover :width="430">
							<template #reference>
								<el-icon size="16"><EpQuestionFilled /></el-icon>
							</template>
							<template #default>Percentage of samples with one or more structural variants</template>
						</el-popover>
					</template>
					<template #default="scope">
						<span class="c" :style="{ '--color': scope.row.color }" v-if="scope.row.freq === '<0.1%'">{{ scope.row.freq }}</span>
						<span class="c" :style="{ '--color': scope.row.color }" v-else>{{ parseFloat(scope.row.freq*100).toFixed(2) }}%</span>
					</template>
				</el-table-column>
				<el-table-column sortable="custom" prop="pmid" label="PMID" minWidth="600">
					<template #default="scope">
						<span v-if="scope.row.pmid === 'NA' || !scope.row.pmid">NA</span>
						<template v-else v-for="(v, index) in scope.row.pmid.split(',')">
							<a class="table-link" :href="scope.row.pmidUrl.split(',')[index]" target="_blank">{{ v }}</a
							>{{ index === scope.row.pmid.split(",").length - 1 ? " " : "," }}
						</template>
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
