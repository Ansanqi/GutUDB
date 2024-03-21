<script setup>
const route = useRoute()
const { dataAccessId } = route.query
const search = ref({})
const tableData = ref([])
const tableData2 = ref([])
const tableDataTitle2 = ref([])
// const getMaxCountN = ref(0)

// 分页
const pagination = reactive({
	current: 1,
	size: 10,
	total: 0,
})

const v = ref("")
getList()
async function getList(order) {
	const {
		data: { total, records },
	} = await api.get("/web/sraRunTable/page", { ...pagination, ...search.value, dataAccessId })
	pagination.total = parseInt(total)

	records.forEach((item) => {
		if (!item.diseaseStage) {
			item.diseaseStage = "-"
		}
	})
	tableData.value = records

	const { data: data2 } = await api.get("/web/sraRunTable/groupInfos", { dataAccessId })

	const { data: data3 } = await api.get("/web/sraRunTable/groupNotes", { dataAccessId })
	v.value = data3

	const arr = []
	// let getMaxCount = 0
	// for (let i = 0, len = data2.length; i < len; i++) {
	// 	const item = data2[i];
	// 	if (getMaxCount < item.cells.length) {
	// 		getMaxCount = item.cells.length
	// 	}
	// }
	// getMaxCountN.value = getMaxCount
	// for (let i = 0; i < getMaxCount; i++) {
	// 	const obj = {}
	// 	for (let j = 0; j < data2.length; j++) {
	// 		obj[`cell${j}`] = ''
	// 	}
	// 	arr.push(obj)
	// }
	// for (let i = 0; i < data2.length; i++) {
	// 	const item = data2[i];
	// 	for (let j = 0; j < item.cells.length; j++) {
	// 		const item2 = item.cells[j];
	// 		arr[j][`cell${i}`] = item2.name
	// 	}
	// }
	if (data2.length > 0) {
		for (const key in data2[0]) {
			// const value = data2[key];
			arr.push(key)
		}
	}

	tableData2.value = data2
	tableDataTitle2.value = arr
}

const down = async () => {
	await api.down("/web/sraRunTable/exportExcel", { ...pagination, ...search.value, dataAccessId, file })
}
const sortChange = async ({ column, prop, order }) => {
	getList({ orderBy: `${prop}_${order}` })
}
</script>

<template>
	<div class="sub_content">
		<!-- <div class="my-tsv" @click="down">
			<span class="iconfont icon-xiazai"></span>
			<span>CSV</span>
		</div>
		<div class="my-search df aic">
			<el-input v-model="search" placeholder="Search Disease, Cell Type, Tissue, Strain" @keyup.enter="getList" />

		</div> -->
		<Search :search="search" :fn="getList" :down="down" title="sraRun">
			<div class="mr20">
				<el-popover :width="1200">
					<template #reference>
						<el-icon size="24"><EpQuestionFilled /></el-icon>
					</template>
					<template #default>
						<el-table :data="tableData2" border size="default">
							<el-table-column sortable="custom" v-for="(item, index) in tableDataTitle2" :key="item" :prop="`cells${index}`" :label="item" align="center">
								<template #default="scope">
									<span>{{ scope.row[tableDataTitle2[index]] }}</span>
								</template>
							</el-table-column>
						</el-table>
						<div class="mt10 notes">{{ v }}</div>
					</template>
				</el-popover>
			</div>
		</Search>

		<div class="my-table">
			<el-table :data="tableData" border stripe size="default" @sort-change="sortChange">
				<el-table-column type="index" label="#" width="55" />
				<el-table-column sortable="custom" show-overflow-tooltip prop="disease" label="Disease" min-width="120" />
				<el-table-column sortable="custom" show-overflow-tooltip prop="diseaseStage" label="Disease Stage" min-width="120" />
				<el-table-column sortable="custom" show-overflow-tooltip prop="celType" label="Cell Type" min-width="120" />
				<el-table-column sortable="custom" show-overflow-tooltip prop="tissue" label="Tissue" min-width="120" />
				<el-table-column sortable="custom" show-overflow-tooltip prop="strain" label="Strain" min-width="120" />
				<el-table-column sortable="custom" show-overflow-tooltip prop="treatment" label="Treatment" min-width="120" />
				<el-table-column sortable="custom" show-overflow-tooltip prop="time" label="Time" min-width="120" />
				<el-table-column sortable="custom" show-overflow-tooltip prop="age" label="Age" min-width="120" />
				<el-table-column sortable="custom" show-overflow-tooltip prop="run" label="Run" min-width="120" />
			</el-table>
			<Pagination v-model:page="pagination.current" v-model:size="pagination.size" :total="pagination.total" @page-change="getList()"></Pagination>
		</div>
	</div>
</template>

<style lang="scss" scoped>
.notes {
	word-break: break-word;
}
@import url("@/styles/content.scss");
</style>
