<script setup>
const props = defineProps({
	projectNo: String,
})

// 分页
const pagination = reactive({
	current: 1,
	size: 5,
	total: 0,
})
const pagination2 = reactive({
	current: 1,
	size: 5,
	total: 0,
})

const imageArr1 = ref()
const imageArr2 = ref()

const tableData = ref([])
const tableData2 = ref([])

const sampleT = ref("")

async function getList(sample, order) {
	sampleT.value = sample
	// if (navIndex.value == 0) {
		const {
			data: { records, total },
		} = await api.get("/web/aRnaCleanedLogTpm/page", { ...pagination, project: props.projectNo, sample, ...order })
		pagination.total = parseInt(total)
		tableData.value = records
		imageArr1.value = picUrl(records[0].imgUrl)
	// } else {
		const {
			data: { records:records2, total:total2 },
		} = await api.get("/web/dRnaTsScore/page", { ...pagination2, project: props.projectNo, sample, ...order })
		pagination2.total = parseInt(total2)
		tableData2.value = records2
		imageArr2.value = picUrl(records2[0].imgUrl)
	// }
}

defineExpose({ getList })

const navList = ["TPM Exp", "TS Score"]
// const navIndex = ref(0)
// const handleCurrentClick = (index) => {
// 	navIndex.value = index
// 	getList(sampleT.value)
// }
const sortChange = async ({ column, prop, order }) => {
	getList('', { orderBy: `${prop}_${order}` })
}

const cellMouseEnter = (row) => {
	imageArr1.value = picUrl(row.imgUrl)
}
const cellMouseEnter2 = (row) => {
	imageArr2.value = picUrl(row.imgUrl)
}
</script>

<template>
	<div class="my-table">
		<el-tabs id="profile-1-0" v-model="navList[0]">
			<el-tab-pane :label="navList[0]" :name="navList[0]">
				<div class="tips">Gene expression data</div>
				<el-table :data="tableData" border stripe size="default" @sort-change="sortChange" @cell-mouse-enter="cellMouseEnter">
					<el-table-column type="index" label="#" width="55" />
					<el-table-column sortable="custom" show-overflow-tooltip prop="geneName" min-width="120" label="gene_name">
						<template #default="scope">
							<router-link :to="{ path: '/gene/index', query: { geneName: scope.row.geneName } }" class="table-link">{{ scope.row.geneName }}</router-link>
						</template>
					</el-table-column>
					<el-table-column sortable="custom" show-overflow-tooltip prop="adrenalGland" min-width="150" label="Adrenal_Gland" />
					<el-table-column sortable="custom" show-overflow-tooltip prop="spleen" min-width="120" label="Spleen" />
					<el-table-column sortable="custom" show-overflow-tooltip prop="heartAtrialAppendage" min-width="230" label="Heart_Atrial_Appendage" />
					<el-table-column sortable="custom" show-overflow-tooltip prop="pancreas" min-width="120" label="Pancreas" />
					<el-table-column sortable="custom" show-overflow-tooltip prop="arteryAorta" min-width="150" label="Artery_Aorta" />
					<el-table-column sortable="custom" show-overflow-tooltip prop="breastMammaryTissue" min-width="230" label="Breast_Mammary_Tissue" />
					<el-table-column sortable="custom" show-overflow-tooltip prop="smallIntestineTerminalIleum" min-width="280" label="Small_Intestine_Terminal_Ileum" />
					<el-table-column sortable="custom" show-overflow-tooltip prop="colonSigmoid" min-width="160" label="Colon_Sigmoid" />
					<el-table-column sortable="custom" show-overflow-tooltip prop="esophagusMucosa" min-width="190" label="Esophagus_Mucosa" />
					<el-table-column sortable="custom" show-overflow-tooltip prop="esophagusMuscularis" min-width="210" label="Esophagus_Muscularis" />
					<el-table-column sortable="custom" show-overflow-tooltip prop="esophagusGastroesophagealJunction" min-width="340" label="Esophagus_Gastroesophageal_Junction" />
					<el-table-column sortable="custom" show-overflow-tooltip prop="muscleSkeletal" min-width="160" label="Muscle_Skeletal" />
					<el-table-column sortable="custom" show-overflow-tooltip prop="thyroid" min-width="120" label="Thyroid" />
					<el-table-column sortable="custom" show-overflow-tooltip prop="heartLeftVentricle" min-width="190" label="Heart_Left_Ventricle" />
					<el-table-column sortable="custom" show-overflow-tooltip prop="arteryCoronary" min-width="180" label="Artery_Coronary" />
					<el-table-column sortable="custom" show-overflow-tooltip prop="stomach" min-width="120" label="Stomach" />
					<el-table-column sortable="custom" show-overflow-tooltip prop="uterus" min-width="120" label="Uterus" />
					<el-table-column sortable="custom" show-overflow-tooltip prop="vagina" min-width="120" label="Vagina" />
					<el-table-column sortable="custom" show-overflow-tooltip prop="colonTransverse" min-width="180" label="Colon_Transverse" />
					<el-table-column sortable="custom" show-overflow-tooltip prop="skinNotSunExposedSuprapubic" min-width="320" label="Skin_Not_Sun_Exposed(Suprapubic)" />
					<el-table-column sortable="custom" show-overflow-tooltip prop="nerveTibial" min-width="160" label="Nerve_Tibial" />
					<el-table-column sortable="custom" show-overflow-tooltip prop="arteryTibial" min-width="160" label="Artery_Tibial" />
					<el-table-column sortable="custom" show-overflow-tooltip prop="ovary" min-width="120" label="Ovary" />
					<el-table-column sortable="custom" show-overflow-tooltip prop="liver" min-width="120" label="Liver" />
					<el-table-column sortable="custom" show-overflow-tooltip prop="lung" min-width="120" label="Lung" />
					<el-table-column sortable="custom" show-overflow-tooltip prop="brainCerebellum" min-width="170" label="Brain_Cerebellum" />
					<el-table-column sortable="custom" show-overflow-tooltip prop="brainCortex" min-width="140" label="Brain_Cortex" />
					<el-table-column sortable="custom" show-overflow-tooltip prop="skinSunExposedLowerLeg" min-width="270" label="Skin_Sun_Exposed(Lower_leg)" />
					<el-table-column sortable="custom" show-overflow-tooltip prop="pituitary" min-width="120" label="Pituitary" />
					<el-table-column sortable="custom" show-overflow-tooltip prop="testis" min-width="120" label="Testis" />
					<el-table-column sortable="custom" show-overflow-tooltip prop="prostate" min-width="120" label="Prostate" />
					<el-table-column sortable="custom" show-overflow-tooltip prop="minorSalivaryGland" min-width="210" label="Minor_Salivary_Gland" />
				</el-table>
				<Pagination v-model:page="pagination.current" v-model:size="pagination.size" :total="pagination.total" @page-change="getList()"></Pagination>
				<div class="bottom" v-if="imageArr1">
					<img :src="imageArr1" />
				</div>
			</el-tab-pane>
		</el-tabs>

		<el-tabs id="profile-1-1" v-model="navList[1]">
			<el-tab-pane :label="navList[1]" :name="navList[1]">
				<div class="tips">Gene expression data</div>
				<el-table :data="tableData2" border size="default" @cell-mouse-enter="cellMouseEnter2">
					<el-table-column sortable="custom" show-overflow-tooltip prop="geneName" min-width="120" label="gene_name">
						<template #default="scope">
							<router-link :to="{ path: '/gene/index', query: { geneName: scope.row.geneName } }" class="table-link">{{ scope.row.geneName }}</router-link>
						</template>
					</el-table-column>
					<el-table-column sortable="custom" show-overflow-tooltip prop="adrenalGland" min-width="150" label="Adrenal_Gland" />
					<el-table-column sortable="custom" show-overflow-tooltip prop="arteryAorta" min-width="150" label="Artery_Aorta" />
					<el-table-column sortable="custom" show-overflow-tooltip prop="arteryCoronary" min-width="160" label="Artery_Coronary" />
					<el-table-column sortable="custom" show-overflow-tooltip prop="arteryTibial" min-width="150" label="Artery_Tibial" />
					<el-table-column sortable="custom" show-overflow-tooltip prop="brainCerebellum" min-width="170" label="Brain_Cerebellum" />
					<el-table-column sortable="custom" show-overflow-tooltip prop="brainCortex" min-width="130" label="Brain_Cortex" />
					<el-table-column sortable="custom" show-overflow-tooltip prop="breast" min-width="120" label="Breast" />
					<el-table-column sortable="custom" show-overflow-tooltip prop="colonSigmoid" min-width="150" label="Colon_Sigmoid" />
					<el-table-column sortable="custom" show-overflow-tooltip prop="colonTransverse" min-width="170" label="Colon_Transverse" />
					<el-table-column sortable="custom" show-overflow-tooltip prop="geJunction" min-width="120" label="GE_junction" />
					<el-table-column sortable="custom" show-overflow-tooltip prop="esophagusMucosa" min-width="190" label="Esophagus_Mucosa" />
					<el-table-column sortable="custom" show-overflow-tooltip prop="esophagusMuscle" min-width="190" label="Esophagus_Muscle" />
					<el-table-column sortable="custom" show-overflow-tooltip prop="heartAtrial" min-width="150" label="Heart_Atrial" />
					<el-table-column sortable="custom" show-overflow-tooltip prop="heartVentricle" min-width="150" label="Heart_Ventricle" />
					<el-table-column sortable="custom" show-overflow-tooltip prop="liver" min-width="120" label="Liver" />
					<el-table-column sortable="custom" show-overflow-tooltip prop="lung" min-width="120" label="Lung" />
					<el-table-column sortable="custom" show-overflow-tooltip prop="minorSalivary" min-width="150" label="Minor_Salivary" />
					<el-table-column sortable="custom" show-overflow-tooltip prop="muscleSkeletal" min-width="160" label="Muscle_Skeletal" />
					<el-table-column sortable="custom" show-overflow-tooltip prop="nerveTibial" min-width="130" label="Nerve_Tibial" />
					<el-table-column sortable="custom" show-overflow-tooltip prop="ovary" min-width="120" label="Ovary" />
					<el-table-column sortable="custom" show-overflow-tooltip prop="pancreas" min-width="120" label="Pancreas" />
					<el-table-column sortable="custom" show-overflow-tooltip prop="pituitary" min-width="120" label="Pituitary" />
					<el-table-column sortable="custom" show-overflow-tooltip prop="prostate" min-width="120" label="Prostate" />
					<el-table-column sortable="custom" show-overflow-tooltip prop="skinUnexpo" min-width="130" label="Skin_Unexpo" />
					<el-table-column sortable="custom" show-overflow-tooltip prop="skinSunexpo" min-width="140" label="Skin_SunExpo" />
					<el-table-column sortable="custom" show-overflow-tooltip prop="smallIntestine" min-width="150" label="Small_Intestine" />
					<el-table-column sortable="custom" show-overflow-tooltip prop="spleen" min-width="120" label="Spleen" />
					<el-table-column sortable="custom" show-overflow-tooltip prop="stomach" min-width="120" label="Stomach" />
					<el-table-column sortable="custom" show-overflow-tooltip prop="testis" min-width="120" label="Testis" />
					<el-table-column sortable="custom" show-overflow-tooltip prop="thyroid" min-width="120" label="Thyroid" />
					<el-table-column sortable="custom" show-overflow-tooltip prop="uterus" min-width="120" label="Uterus" />
					<el-table-column sortable="custom" show-overflow-tooltip prop="vagina" min-width="120" label="Vagina" />
				</el-table>
				<Pagination v-model:page="pagination2.current" v-model:size="pagination2.size" :total="pagination2.total" @page-change="getList()"></Pagination>
				<div class="bottom" v-if="imageArr2">
					<img :src="imageArr2" />
				</div>
			</el-tab-pane>
		</el-tabs>

		<!-- <div class="button_wrap">
			<template v-for="(v, index) in navList" :key="v">
				<div class="button" :class="{ active: index === navIndex }" @click="handleCurrentClick(index)">{{ v }}</div>
			</template>
		</div> -->
	</div>
</template>

<style lang="scss" scoped>
@import url("@/styles/content.scss");

.title {
	padding-left: 10px;
	border-left: 4px solid $themeColor;
	margin-bottom: 10px;
	font-size: $fontSize20;
	font-weight: bold;
}
.content > div {
	margin-bottom: 40px;
}
.my-nav {
	margin-top: 30px;
}
.summary {
	.con {
		background: $backgroundColor;
		padding: 20px 30px;
		display: grid;
		grid-template-columns: 1fr 1fr 1fr;
		.item {
			margin-bottom: 10px;
			&.mb-0 {
				margin-bottom: 0;
			}
		}
	}
}

.button_wrap {
	display: flex;
	margin-bottom: 30px;
	gap: 20px;
	.button {
		cursor: pointer;
		color: white;
		padding: 10px;
		height: 40px;
		border-radius: 4px;
		background-color: rgba(3, 147, 174, 0.05);
		color: #333333;
	}
	.active {
		background-color: $themeColor;
		color: white;
	}
}
.tips {
	color: $themeColor;
	line-height: 3;
	// margin-top: -20px;
}
.bottom {
	margin-top: 40px;
	display: grid;
	grid-template-columns: 1fr 1fr; /* 一行两列，每列平均分配剩余空间 */
	gap: 20px;
	img {
		max-width: 100%; /* 图片宽度最大为容器的剩余空间 */
		height: auto; /* 确保图片按比例缩放 */
	}
}
</style>
