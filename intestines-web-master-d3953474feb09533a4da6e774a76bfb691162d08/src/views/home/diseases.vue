<script setup>
const tableData = ref([])
// 分页
const pagination = reactive({
	current: 1,
	size: 10,
	total: 50,
})

const searchObj = ref({})
const search = reactive({
	diseaseName: "",
	omicsLevel: "",
	hotGenes: ""
})
const omicsLevels = ref([])
const omicsLevels2 = ref([])
const omicsLevelsActive = ref([])
const choose = (type, data, isChild) => {
	search[`${type}`] = data.name
	if (isChild == 1) {
		omicsLevelsActive.value = data.name
		if (data.child) {
			omicsLevels2.value = data.child
			omicsLevelsActive.value = data.child[0].name
		} else {
			omicsLevels2.value = []
			omicsLevelsActive.value = data.name
		}
	} else if (isChild == 2) {
		omicsLevelsActive.value = data.name
	} else {
		search[`${type}`] = data.name
		if (type === "omicsLevel") {
			if (data.child) {
				omicsLevels.value = data.child
				omicsLevels2.value = []
				omicsLevelsActive.value = data.child[0].name
			} else {
				omicsLevels.value = []
				omicsLevels2.value = []
				omicsLevelsActive.value = data.name
			}
		}
	}
	getList()
}
const isFlod = ref(true)

init()
async function init() {
	await getSearch()
	await getList()
}
async function getSearch() {
	const { data } = await api.get("/web/geneDiseaseOmics/colonDiseaseSearchHeader")
	searchObj.value = data
	search.diseaseName = data.diseases[0].name
	search.omicsLevel = data.omicsLevels[0].name
	search.hotGenes = data.hotGeness[0].name
}

async function getList(order) {
	const {
		data: { total, records },
	} = await api.get("/web/geneDiseaseOmics/page", { ...pagination, ...search, ...order })
	// omicsLevel: omicsLevelsActive.value,
	pagination.total = parseInt(total)
	tableData.value = records
}
const down = async (exportCurrent, exportSize) => {
	await api.down("/web/geneDiseaseOmics/exportExcel", { ...pagination, ...search, exportCurrent, exportSize })
}
const sortChange = async ({ column, prop, order }) => {
	getList({ orderBy: `${prop}_${order}` })
}
</script>

<template>
	<div class="content">
		<Title />
		<el-card :class="{ hide: !isFlod }">
			<div class="p">
				<div class="label">Diseases name :</div>
				<div class="list">
					<div class="item" v-for="v in searchObj.diseases" :key="v" :class="{ active: search.diseaseName === v.name }" @click="choose('diseaseName', v)">
						{{ `${v.name}(${v.count})` }}
					</div>
				</div>
			</div>
			<div class="p">
				<div class="label">Omics level:</div>
				<div class="list">
					<div class="item" v-for="v in searchObj.omicsLevels" :key="v" :class="{ active: search.omicsLevel === v.name }" @click="choose('omicsLevel', v)">{{ v.name }}</div>
				</div>
			</div>
			<div class="p" v-if="omicsLevels.length">
				<div class="label"></div>
				<div class="list">
					<div class="item" v-for="v in omicsLevels" :key="v" :class="{ active: search.omicsLevel === v.name }" @click="choose('omicsLevel', v, 1)">{{ v.name }}</div>
				</div>
			</div>
			<div class="p" v-if="omicsLevels2.length">
				<div class="label"></div>
				<div class="list">
					<div class="item" v-for="v in omicsLevels2" :key="v" :class="{ active: search.omicsLevel === v.name }" @click="choose('omicsLevel', v, 2)">{{ v.name }}</div>
				</div>
			</div>
			<div class="p">
				<div class="label">Hot genes:</div>
				<div class="list">
					<div class="item" v-for="v in searchObj.hotGeness" :key="v" :class="{ active: search.hotGenes === v.name }" @click="choose('hotGenes', v)">
						{{ `${v.name}(${v.count})` }}
					</div>
				</div>
			</div>
		</el-card>
		<div class="zk" @click="isFlod = !isFlod">
			<span>
				{{ isFlod ? "fold" : "unfold" }}
				<img :class="{ un: !isFlod }" src="../../assets/images/flod.webp" />
			</span>
		</div>

		<div class="con">
			<Search :search="search" :fn="getList" :down="down" :downPage="pagination.total" />
			<div class="my-table">
				<el-table :data="tableData" border stripe size="default" @sort-change="sortChange">
					<el-table-column type="index" label="#" width="55" />
					<el-table-column sortable="custom" show-overflow-tooltip prop="diseaseRelatedGenes" label="Disease related genes" minWidth="140">
						<template #default="scope">
							<!-- <router-link class="table-link" :to="{ path: '/gene/index', query: { geneName: scope.row.diseaseRelatedGenes } }">{{ scope.row.diseaseRelatedGenes }}</router-link> -->
							<template v-for="(v, index) in scope.row.diseaseRelatedGenes.split('|')">
								<router-link class="table-link" :to="{ path: '/gene/index', query: { geneName: v } }">{{ v }}</router-link>{{ index === scope.row.diseaseRelatedGenes.split("|").length - 1 ? "" : " | " }}
							</template>
						</template>
					</el-table-column>
					<el-table-column sortable="custom" show-overflow-tooltip prop="disease" label="Disease" minWidth="240" />
					<el-table-column sortable="custom" show-overflow-tooltip prop="omics" label="Omics" minWidth="160" />
					<el-table-column sortable="custom" show-overflow-tooltip prop="source" label="Source" minWidth="410">
						<template #default="scope">
							<a class="table-link" :href="scope.row.sources" target="_blank">{{ scope.row.sources }}</a>
						</template>
					</el-table-column>
				</el-table>
				<Pagination v-model:page="pagination.current" v-model:size="pagination.size" :total="pagination.total" @page-change="getList()"></Pagination>
			</div>
		</div>
	</div>
</template>

<style lang="scss" scoped>
@import url("@/styles/content.scss");
.el-card {
	position: relative;
	&.hide {
		height: 65px;
	}

	.p {
		display: flex;
		margin-bottom: 15px;
		line-height: 2.5;
		&:last-child {
			margin-bottom: 0;
		}
		.label {
			min-width: 140px;
			color: $themeColor;
		}
		.list {
			display: flex;
			flex-wrap: wrap;
			.item {
				margin-right: 30px;
				cursor: pointer;
				&.active {
					color: $themeColor;
				}
			}
		}
	}
}
.zk {
	position: relative;
	display: inline-block;
	margin: -2px auto 40px;
	border: 1px solid #e4e7ed;
	border-top: none;
	padding: 5px 20px;
	background: #fff;
	z-index: 10;
	left: 50%;
	transform: translateX(-50%);
	cursor: pointer;
	color: #999;
	span {
		display: flex;
		align-items: center;
		img {
			height: 14px;
			margin-left: 5px;
			&.un {
				transform: rotate(180deg);
			}
		}
	}
}
.con {
	position: relative;
	.my-tsv {
		top: 5px;
		right: 0;
	}
}
</style>
