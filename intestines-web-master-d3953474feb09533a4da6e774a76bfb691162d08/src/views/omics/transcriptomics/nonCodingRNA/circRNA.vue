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
	} = await api.get("/web/circRna/page", { ...pagination, ...search.value, ...order })
	pagination.total = parseInt(total)
	tableData.value = records
}

const down = async () => {
	await api.down("/web/circRna/exportExcel", { ...pagination,...search.value })
}
const sortChange = async ({ column, prop, order }) => {
	getList({ orderBy: `${prop}_${order}` })
}
</script>

<template>
	<div class="sub_content">
		<Search :search="search" :fn="getList" :down="down" title="circRNA"/>
		<div class="my-table">
			<el-table :data="tableData" border stripe size="default" @sort-change="sortChange">
				<el-table-column type="index" label="#" width="55" />
				<el-table-column sortable="custom" show-overflow-tooltip prop="categories" label="Category" min-width="120" />
				<el-table-column sortable="custom" show-overflow-tooltip prop="geneName" label="Gene Name" min-width="130">
					<template #default="scope">
						<span v-if="scope.row.geneName === 'NA' || !scope.row.geneName">{{ scope.row.geneName }}</span>
						<router-link v-else :to="{ path: '/gene/index', query: { geneName: scope.row.geneName } }" class="table-link">{{ scope.row.geneName }}</router-link>
					</template>
				</el-table-column>
				<el-table-column sortable="custom" show-overflow-tooltip prop="disease" label="Disease" min-width="150" />
				<el-table-column sortable="custom" show-overflow-tooltip prop="species" label="Species" min-width="150" />
				<el-table-column sortable="custom" show-overflow-tooltip prop="sample" label="Sample" min-width="280" />
				<el-table-column sortable="custom" show-overflow-tooltip prop="dysfunctionPattern" label="Dysfunction Pattern" min-width="280" />
				<el-table-column sortable="custom" show-overflow-tooltip prop="validatedMethod" label="Validated Method" min-width="240" />
				<el-table-column sortable="custom" show-overflow-tooltip prop="description" label="Description" min-width="1000" />
				<el-table-column sortable="custom" prop="pmid" label="PMID" minWidth="100">
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
