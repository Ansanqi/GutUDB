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
	} = await api.get("/web/spatialHistology/page", { ...pagination, ...search.value, ...order })
	pagination.total = parseInt(total)
	tableData.value = records
}

const down = async () => {
	await api.down("/web/spatialHistology/exportExcel", { ...pagination,...search.value })
}
const expandRowKeys = ref([])
const expandChange = async (row) => {
	if (row.id === expandRowKeys.value[0]) {
		expandRowKeys.value = []
		return
	}
	expandRowKeys.value = [row.id]
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
			<el-table :data="tableData" border stripe size="default" @expand-change="expandChange" :expand-row-keys="expandRowKeys" row-key="id" @sort-change="sortChange">
				<el-table-column sortable="custom" show-overflow-tooltip type="expand" label="Details" minWidth="90">
					<template #default="scope">
						<div class="details">
							<div class="title mb20">Summary</div>
							<div class="con">
								<el-row>
									<el-col :span="8">
										<span>Gene Name：</span>
										<span>{{ scope.row.geneName }} </span>
									</el-col>
									<el-col :span="8">
										<span>Sample ID：</span>
										<span>{{ scope.row.sampleId }} </span>
									</el-col>
									<el-col :span="8">
										<span>Tissue：</span>
										<span>{{ scope.row.tissue }} </span>
									</el-col>

									<el-col :span="8">
										<span>Biotech Categories：</span>
										<span>{{ scope.row.biotechCategories }} </span>
									</el-col>
									<el-col :span="8">
										<span>Species：</span>
										<span>{{ scope.row.species }} </span>
									</el-col>
									<el-col :span="8">
										<span>Biotech：</span>
										<span>{{ scope.row.biotech }} </span>
									</el-col>

									<el-col :span="8">
										<span>N Unit：</span>
										<span>{{ scope.row.nunit }} </span>
									</el-col>
									<el-col :span="8">
										<span>Expression Range：</span>
										<span>{{ scope.row.expressionRange }} </span>
									</el-col>
									<el-col :span="8">
										<span>Project：</span>
										<span>{{ scope.row.project }} </span>
									</el-col>

									<el-col :span="24">
										<span>Title：</span>
										<span>{{ scope.row.title }}</span>
									</el-col>
								</el-row>
								<div class="imgs">
									<img :src="picUrl(scope.row.annotationImg)" alt="" srcset="" />
									<img :src="picUrl(scope.row.expressionImg)" alt="" srcset="" />
								</div>
							</div>
						</div>
					</template>
				</el-table-column>
				<el-table-column sortable="custom" show-overflow-tooltip prop="geneName" label="Gene Name" minWidth="130">
					<template #default="scope">
						<span v-if="scope.row.geneName === 'NA' || !scope.row.geneName">{{ scope.row.geneName }}</span>
						<router-link v-else :to="{ path: '/gene/index', query: { geneName: scope.row.geneName } }" class="table-link">{{ scope.row.geneName }}</router-link>
					</template>
				</el-table-column>
				<el-table-column sortable="custom" show-overflow-tooltip prop="species" label="Species" minWidth="130" />
				<el-table-column sortable="custom" show-overflow-tooltip prop="tissue" label="Tissue" minWidth="130" />
				<el-table-column sortable="custom" show-overflow-tooltip prop="biotechCategories" label="Biotech Categories" minWidth="210" />
				<el-table-column sortable="custom" show-overflow-tooltip prop="expressionRange" label="Expression Range" minWidth="170" />
				<el-table-column sortable="custom" show-overflow-tooltip prop="biotech" label="Biotech" minWidth="120" />
				<el-table-column sortable="custom" show-overflow-tooltip prop="nunit" label="N Unit" minWidth="100" />
				<el-table-column sortable="custom" show-overflow-tooltip prop="title" label="Title" minWidth="600" />
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
	// border: 1px solid $themeColor;
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
		.el-col {
			span {
				&:last-child {
					font-weight: bold;
				}
			}
		}
	}
	.imgs {
		background-color: #fff;
		display: flex;
		margin-top: 30px;
		img {
			width: 50%;
		}
	}
}
</style>
