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
	} = await api.get("/web/proteomics/page", { ...pagination, ...search.value, ...order })
	pagination.total = parseInt(total)
	tableData.value = records
}

const down = async () => {
	await api.down("/web/proteomics/exportExcel", { ...pagination,...search.value })
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
				<el-table-column sortable="custom" show-overflow-tooltip prop="disease" label="Disease" minWidth="140" />
				<el-table-column sortable="custom" show-overflow-tooltip prop="sapiens" label="Species" minWidth="210" />
				<el-table-column sortable="custom" show-overflow-tooltip prop="position" label="Position" minWidth="140" />
				<el-table-column sortable="custom" show-overflow-tooltip prop="proteinName" label="Protein Name" minWidth="400" />
				<el-table-column sortable="custom" show-overflow-tooltip prop="induction" label="Induction" minWidth="130" />
				<el-table-column sortable="custom" show-overflow-tooltip prop="length" label="Length" minWidth="110" />
				<el-table-column sortable="custom" show-overflow-tooltip prop="postTranslationalModification" label="Post Translational Modification" minWidth="550" />
				<el-table-column sortable="custom" show-overflow-tooltip prop="function" label="Function" minWidth="550" />
				<el-table-column sortable="custom" show-overflow-tooltip prop="activityRegulation" label="Activity Regulation" minWidth="180" />
				<el-table-column sortable="custom" show-overflow-tooltip prop="pathway" label="Pathway" minWidth="120" />
				<el-table-column sortable="custom" prop="pmid" label="PubMed ID" minWidth="7000">
					<template #default="scope">
						<span v-if="scope.row.pmid === 'NA' || !scope.row.pmid">NA</span>
						<template v-else v-for="(v, index) in scope.row.pmid.split(',')">
							<a class="table-link" :href="scope.row.pmidUrl.split(',')[index]" target="_blank">{{ v }}</a>{{ index === scope.row.pmid.split(',').length-1 ? ' ' : ',' }}
						</template>
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
