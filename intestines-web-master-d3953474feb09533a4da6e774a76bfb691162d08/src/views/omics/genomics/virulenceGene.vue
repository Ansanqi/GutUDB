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
	} = await api.get("/web/virulenceGene/page", { ...pagination, ...search.value, ...order })
	pagination.total = parseInt(total)
	tableData.value = records
}

const phenotypeAlert = [
	'Phenotype Mapping Key',
	'1 - The disorder is placed on the map due to its association with a gene, but the underlying defect is not known.',
	'2 - The disorder was placed on the map by statistical methods.',
	'3 - The molecular basis of the disorder is known.',
	'4 - A contiguous gene duplication or deletion syndrome in which multiple genes are involved.',
	'',
	'',
	'Inheritance Abbreviations',
	'?AD',
	'?XLR',
	'AD',
	'AR',
	'PD',
	'PR',
	'DD',
	'DR',
	'ICB',
	'IC',
	'Mi',
	'Mu',
	'SMo',
	'SMu',
	'XL',
	'XLD',
	'XLR',
	'YL',
]

const down = async () => {
	await api.down("/web/virulenceGene/exportExcel", { ...pagination,...search.value })
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
				<el-table-column sortable="custom" show-overflow-tooltip prop="geneName" label="Gene Name" min-width="110">
					<template #default="scope">
						<span v-if="scope.row.geneName === 'NA' || !scope.row.geneName">{{ scope.row.geneName }}</span>
						<router-link v-else :to="{ path: '/gene/index', query: { geneName: scope.row.geneName } }" class="table-link">{{ scope.row.geneName }}</router-link>
					</template>
				</el-table-column>
				<el-table-column sortable="custom" show-overflow-tooltip prop="disease" label="Disease" min-width="120" />
				<el-table-column sortable="custom" show-overflow-tooltip prop="location" label="Location" min-width="100" />
				<el-table-column sortable="custom" show-overflow-tooltip prop="phenotype" label="Phenotype" min-width="200" />
				<el-table-column sortable="custom" show-overflow-tooltip prop="inheritance" label="Inheritance" min-width="120" />
				<el-table-column sortable="custom" show-overflow-tooltip prop="phenotypeMappingKey" label="Phenotype Mapping Key" min-width="150">
					<template #header>
						Phenotype Mapping Key
						<el-popover :width="500">
							<template #reference>
									<el-icon size="16"><EpQuestionFilled /></el-icon>
							</template>
							<template #default>
								<div class="demo-rich-conent" style="display: flex; gap: 10px; flex-direction: column">
									<p v-for="item in phenotypeAlert" class="demo-rich-content__desc" style="margin: 0">{{item}}</p>
								</div>
							</template>
						</el-popover>
					</template>
					<template #default="scope">
						{{ scope.row.phenotypeMappingKey }}
					</template>
				</el-table-column>
			</el-table>
			<Pagination v-model:page="pagination.page" v-model:size="pagination.size" :total="pagination.total" @page-change="getList()"></Pagination>
		</div>
	</div>
</template>

<style lang="scss" scoped>
@import url("@/styles/content.scss");
</style>
