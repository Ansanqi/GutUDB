<script setup>
const search = ref({})
const tableData = ref([])
const keys = ref([])
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
	} = await api.get("/web/mousecolonProtein/page", { ...pagination, ...search.value, ...order })
	pagination.total = parseInt(total)
	tableData.value = records
	delete records[0].id
	keys.value = Object.keys(records[0])
}

const down = async () => {
	await api.down("/web/mousecolonProtein/exportExcel", { ...pagination,...search.value })
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
			<el-table :data="tableData" border stripe>
				<template v-for="v in keys" :key="v">
					<el-table-column show-overflow-tooltip :prop="v" :label="v.slice(2)" :minWidth="v === 'cellPhenotypicMarkers' ? '380': '90'" />
				</template>
			</el-table>
			<Pagination v-model:page="pagination.current" v-model:size="pagination.size" :total="pagination.total" @page-change="getList()" />
		</div>
	</div>
</template>

<style lang="scss" scoped>
@import url("@/styles/content.scss");

.details {
	margin: 10px 20px;
	padding: 10px 20px;
	border-radius: 4px;
	box-shadow: 0 0 2px #ccc;
	.title {
		border-left: 3px solid $themeColor;
		padding-left: 10px;
		font-size: $fontSize18;
		font-weight: bold;
	}
	.con {
		line-height: 2;
		overflow-x: auto;
		border-top: 1px solid $separatorColor;
		border-left: 1px solid $separatorColor;
		display: flex;
		flex-direction: column;
		.el-table1 {
			display: flex;
			div {
				p {
					width: 80px;
					padding: 0 12px;
					border-right: 1px solid $separatorColor;
					border-bottom: 1px solid $separatorColor;
					&:first-child {
						// background-color: #e9f4fb;
					}
					&:last-child {
						background-color: #fff;
					}
				}
			}
		}
	}
}
</style>
