<script setup>
const props = defineProps({
	id: String,
})
const data = inject("data")

const navList = ["TPM Exp", "TS Score"]
// const navIndex = ref(0)

const tpmExpsImg = ref()
const tsScoreImg = ref()
const cellMouseEnter = (row) => {
	tpmExpsImg.value = picUrl(row.imgUrl)
	console.log(tpmExpsImg.value)
}
const cellMouseEnter1 = (row) => {
	tsScoreImg.value = picUrl(row.imgUrl)
}
</script>

<template>
	<div class="my-table">
		<template v-for="(v, index) in navList">
			<el-tabs v-model="navList[index]" :id="props.id + '-' + index">
				<el-tab-pane :label="v" :name="v">
					<template v-if="index === 0">
						<div class="tips">Gene expression data</div>
						<el-table :data="data.tpmExps" border size="default" @cell-mouse-enter="cellMouseEnter">
							<!-- <el-table-column show-overflow-tooltip prop="geneName" label="gene_name" minWidth="120" /> -->
							<el-table-column show-overflow-tooltip prop="adrenalGland" label="Adrenal_Gland" minWidth="150" />
							<el-table-column show-overflow-tooltip prop="spleen" label="Spleen" minWidth="80" />
							<el-table-column show-overflow-tooltip prop="heartAtrialAppendage" label="Heart_Atrial_Appendage" minWidth="230" />
							<el-table-column show-overflow-tooltip prop="pancreas" label="Pancreas" minWidth="100" />
							<el-table-column show-overflow-tooltip prop="arteryAorta" label="Artery_Aorta" minWidth="130" />
							<el-table-column show-overflow-tooltip prop="breastMammaryTissue" label="Breast_Mammary_Tissue" minWidth="230" />
							<el-table-column show-overflow-tooltip prop="smallIntestineTerminalIleum" label="Small_Intestine_Terminal_Ileum" minWidth="280" />
							<el-table-column show-overflow-tooltip prop="colonSigmoid" label="Colon_Sigmoid" minWidth="150" />
							<el-table-column show-overflow-tooltip prop="esophagusMuscularis" label="Esophagus_Mucosa" minWidth="190" />
							<el-table-column show-overflow-tooltip prop="Esophagus_Muscularis" label="Esophagus_Muscularis" minWidth="210" />
							<el-table-column show-overflow-tooltip prop="esophagusGastroesophagealJunction" label="Esophagus_Gastroesophageal_Junction" minWidth="340" />
							<el-table-column show-overflow-tooltip prop="muscleSkeletal" label="Muscle_Skeletal" minWidth="160" />
							<el-table-column show-overflow-tooltip prop="thyroid" label="Thyroid" minWidth="100" />
							<el-table-column show-overflow-tooltip prop="heartLeftVentricle" label="Heart_Left_Ventricle" minWidth="200" />
							<el-table-column show-overflow-tooltip prop="arteryCoronary" label="Artery_Coronary" minWidth="160" />
							<el-table-column show-overflow-tooltip prop="stomach" label="Stomach" minWidth="100" />
							<el-table-column show-overflow-tooltip prop="uterus" label="Uterus" minWidth="100" />
							<el-table-column show-overflow-tooltip prop="vagina" label="Vagina" minWidth="100" />
							<el-table-column show-overflow-tooltip prop="colonTransverse" label="Colon_Transverse" minWidth="170" />
							<el-table-column show-overflow-tooltip prop="skinNotSunExposedSuprapubic" label="Skin_Not_Sun_Exposed(Suprapubic)" minWidth="320" />
							<el-table-column show-overflow-tooltip prop="nerveTibial" label="Nerve_Tibial" minWidth="130" />
							<el-table-column show-overflow-tooltip prop="arteryTibial" label="Artery_Tibial" minWidth="130" />
							<el-table-column show-overflow-tooltip prop="ovary" label="Ovary" minWidth="90" />
							<el-table-column show-overflow-tooltip prop="liver" label="Liver" minWidth="90" />
							<el-table-column show-overflow-tooltip prop="lung" label="Lung" minWidth="90" />
							<el-table-column show-overflow-tooltip prop="brainCerebellum" label="Brain_Cerebellum" minWidth="170" />
							<el-table-column show-overflow-tooltip prop="brainCortex" label="Brain_Cortex" minWidth="150" />
							<el-table-column show-overflow-tooltip prop="skinSunExposedLowerLeg" label="Skin_Sun_Exposed(Lower_leg)" minWidth="270" />
							<el-table-column show-overflow-tooltip prop="pituitary" label="Pituitary" minWidth="110" />
							<el-table-column show-overflow-tooltip prop="testis" label="Testis" minWidth="90" />
							<el-table-column show-overflow-tooltip prop="prostate" label="Prostate" minWidth="100" />
							<el-table-column show-overflow-tooltip prop="minorSalivaryGland" label="Minor_Salivary_Gland" minWidth="220" />
						</el-table>

						<el-image :src="tpmExpsImg || picUrl(data.tpmExps[0].imgUrl)" v-if="tpmExpsImg || (data.tpmExps && data.tpmExps.length)">
							<template #error>
								<div class="image-slot">
									<el-empty description="No Data" />
								</div>
							</template>
						</el-image>
						<el-empty v-else />
					</template>
					<template v-else>
						<div class="tips">Gene expression data</div>
						<el-table :data="data.tsScore" border size="default" @cell-mouse-enter="cellMouseEnter1">
							<!-- <el-table-column show-overflow-tooltip prop="geneName" label="gene_name" minWidth="120" /> -->
							<el-table-column show-overflow-tooltip prop="adrenalGland" label="Adrenal_Gland" minWidth="150" />
							<el-table-column show-overflow-tooltip prop="arteryAorta" label="Artery_Aorta" minWidth="130" />
							<el-table-column show-overflow-tooltip prop="arteryCoronary" label="Artery_Coronary" minWidth="160" />
							<el-table-column show-overflow-tooltip prop="arteryTibial" label="Artery_Tibial" minWidth="130" />
							<el-table-column show-overflow-tooltip prop="brainCerebellum" label="Brain_Cerebellum" minWidth="170" />
							<el-table-column show-overflow-tooltip prop="brainCortex" label="Brain_Cortex" minWidth="150" />
							<el-table-column show-overflow-tooltip prop="breast" label="Breast" minWidth="110" />
							<el-table-column show-overflow-tooltip prop="colonSigmoid" label="Colon_Sigmoid" minWidth="150" />
							<el-table-column show-overflow-tooltip prop="colonTransverse" label="Colon_Transverse" minWidth="170" />
							<el-table-column show-overflow-tooltip prop="geJunction" label="GE_junction" minWidth="160" />
							<el-table-column show-overflow-tooltip prop="esophagusMucosa" label="Esophagus_Mucosa" minWidth="190" />
							<el-table-column show-overflow-tooltip prop="esophagusMuscle" label="Esophagus_Muscle" minWidth="190" />
							<el-table-column show-overflow-tooltip prop="heartAtrial" label="Heart_Atrial" minWidth="150" />
							<el-table-column show-overflow-tooltip prop="heartVentricle" label="Heart_Ventricle" minWidth="180" />
							<el-table-column show-overflow-tooltip prop="liver" label="Liver" minWidth="90" />
							<el-table-column show-overflow-tooltip prop="lung" label="Lung" minWidth="90" />

							<el-table-column show-overflow-tooltip prop="minorSalivary" label="Minor_Salivary" minWidth="150" />
							<el-table-column show-overflow-tooltip prop="muscleSkeletal" label="Muscle_Skeletal" minWidth="160" />
							<el-table-column show-overflow-tooltip prop="nerveTibial" label="Nerve_Tibial" minWidth="130" />
							<el-table-column show-overflow-tooltip prop="ovary" label="Ovary" minWidth="90" />
							<el-table-column show-overflow-tooltip prop="pancreas" label="Pancreas" minWidth="100" />
							<el-table-column show-overflow-tooltip prop="pituitary" label="Pituitary" minWidth="100" />
							<el-table-column show-overflow-tooltip prop="prostate" label="Prostate" minWidth="100" />
							<el-table-column show-overflow-tooltip prop="skinUnexpo" label="Skin_Unexpo" minWidth="130" />
							<el-table-column show-overflow-tooltip prop="skinSunexpo" label="Skin_SunExpo" minWidth="140" />
							<el-table-column show-overflow-tooltip prop="smallIntestine" label="Small_Intestine" minWidth="150" />
							<el-table-column show-overflow-tooltip prop="spleen" label="Spleen" minWidth="90" />
							<el-table-column show-overflow-tooltip prop="stomach" label="Stomach" minWidth="100" />
							<el-table-column show-overflow-tooltip prop="testis" label="Testis" minWidth="90" />
							<el-table-column show-overflow-tooltip prop="thyroid" label="Thyroid" minWidth="90" />
							<el-table-column show-overflow-tooltip prop="uterus" label="Uterus" minWidth="90" />
							<el-table-column show-overflow-tooltip prop="vagina" label="Vagina" minWidth="90" />
						</el-table>
						<el-image :src="tsScoreImg || picUrl(data.tsScore[0].imgUrl)" v-if="tsScoreImg || (data.tsScore && data.tsScore.length)">
							<template #error>
								<div class="image-slot">
									<el-empty description="No Data" />
								</div>
							</template>
						</el-image>
						<el-empty v-else />
					</template>
				</el-tab-pane>
			</el-tabs>
		</template>

		<!-- <div class="button_wrap">
			<template v-for="(v, index) in navList" :key="v">
				<div class="button" :class="{ active: index === navIndex }" @click="navIndex = index">{{ v }}</div>
			</template>
		</div>
		<div class="tips">Gene expression data</div>
		<template v-if="navIndex === 0">
			<el-table :data="data.tpmExps" border size="default">
				<el-table-column show-overflow-tooltip prop="geneName" label="gene_name" minWidth="120" />
				<el-table-column show-overflow-tooltip prop="adrenalGland" label="Adrenal_Gland" minWidth="150" />
				<el-table-column show-overflow-tooltip prop="spleen" label="Spleen" minWidth="80" />
				<el-table-column show-overflow-tooltip prop="heartAtrialAppendage" label="Heart_Atrial_Appendage" minWidth="230" />
				<el-table-column show-overflow-tooltip prop="pancreas" label="Pancreas" minWidth="100" />
				<el-table-column show-overflow-tooltip prop="arteryAorta" label="Artery_Aorta" minWidth="130" />
				<el-table-column show-overflow-tooltip prop="breastMammaryTissue" label="Breast_Mammary_Tissue" minWidth="230" />
				<el-table-column show-overflow-tooltip prop="smallIntestineTerminalIleum" label="Small_Intestine_Terminal_Ileum" minWidth="280" />
				<el-table-column show-overflow-tooltip prop="colonSigmoid" label="Colon_Sigmoid" minWidth="150" />
				<el-table-column show-overflow-tooltip prop="esophagusMuscularis" label="Esophagus_Mucosa" minWidth="190" />
				<el-table-column show-overflow-tooltip prop="Esophagus_Muscularis" label="Esophagus_Muscularis" minWidth="210" />
				<el-table-column show-overflow-tooltip prop="esophagusGastroesophagealJunction" label="Esophagus_Gastroesophageal_Junction" minWidth="340" />
				<el-table-column show-overflow-tooltip prop="muscleSkeletal" label="Muscle_Skeletal" minWidth="160" />
				<el-table-column show-overflow-tooltip prop="thyroid" label="Thyroid" minWidth="100" />
				<el-table-column show-overflow-tooltip prop="heartLeftVentricle" label="Heart_Left_Ventricle" minWidth="200" />
				<el-table-column show-overflow-tooltip prop="arteryCoronary" label="Artery_Coronary" minWidth="160" />
				<el-table-column show-overflow-tooltip prop="stomach" label="Stomach" minWidth="100" />
				<el-table-column show-overflow-tooltip prop="uterus" label="Uterus" minWidth="100" />
				<el-table-column show-overflow-tooltip prop="vagina" label="Vagina" minWidth="100" />
				<el-table-column show-overflow-tooltip prop="colonTransverse" label="Colon_Transverse" minWidth="170" />
				<el-table-column show-overflow-tooltip prop="skinNotSunExposedSuprapubic" label="Skin_Not_Sun_Exposed(Suprapubic)" minWidth="320" />
				<el-table-column show-overflow-tooltip prop="nerveTibial" label="Nerve_Tibial" minWidth="130" />
				<el-table-column show-overflow-tooltip prop="arteryTibial" label="Artery_Tibial" minWidth="130" />
				<el-table-column show-overflow-tooltip prop="ovary" label="Ovary" minWidth="90" />
				<el-table-column show-overflow-tooltip prop="liver" label="Liver" minWidth="90" />
				<el-table-column show-overflow-tooltip prop="lung" label="Lung" minWidth="90" />
				<el-table-column show-overflow-tooltip prop="brainCerebellum" label="Brain_Cerebellum" minWidth="170" />
				<el-table-column show-overflow-tooltip prop="brainCortex" label="Brain_Cortex" minWidth="150" />
				<el-table-column show-overflow-tooltip prop="skinSunExposedLowerLeg" label="Skin_Sun_Exposed(Lower_leg)" minWidth="270" />
				<el-table-column show-overflow-tooltip prop="pituitary" label="Pituitary" minWidth="110" />
				<el-table-column show-overflow-tooltip prop="testis" label="Testis" minWidth="90" />
				<el-table-column show-overflow-tooltip prop="prostate" label="Prostate" minWidth="100" />
				<el-table-column show-overflow-tooltip prop="minorSalivaryGland" label="Minor_Salivary_Gland" minWidth="220" />
			</el-table>
			<img :src="picUrl(data.tpmExps?.imgUrl)" alt="" srcset="" />
		</template>
		<template v-else>
			<el-table :data="data.tsScore" border size="default">
				<el-table-column show-overflow-tooltip prop="geneName" label="gene_name" minWidth="120" />
				<el-table-column show-overflow-tooltip prop="adrenalGland" label="Adrenal_Gland" minWidth="150" />
				<el-table-column show-overflow-tooltip prop="arteryAorta" label="Artery_Aorta" minWidth="130" />
				<el-table-column show-overflow-tooltip prop="arteryCoronary" label="Artery_Coronary" minWidth="160" />
				<el-table-column show-overflow-tooltip prop="arteryTibial" label="Artery_Tibial" minWidth="130" />
				<el-table-column show-overflow-tooltip prop="brainCerebellum" label="Brain_Cerebellum" minWidth="170" />
				<el-table-column show-overflow-tooltip prop="brainCortex" label="Brain_Cortex" minWidth="150" />
				<el-table-column show-overflow-tooltip prop="breast" label="Breast" minWidth="110" />
				<el-table-column show-overflow-tooltip prop="colonSigmoid" label="Colon_Sigmoid" minWidth="150" />
				<el-table-column show-overflow-tooltip prop="colonTransverse" label="Colon_Transverse" minWidth="170" />
				<el-table-column show-overflow-tooltip prop="geJunction" label="GE_junction" minWidth="160" />
				<el-table-column show-overflow-tooltip prop="esophagusMucosa" label="Esophagus_Mucosa" minWidth="190" />
				<el-table-column show-overflow-tooltip prop="esophagusMuscle" label="Esophagus_Muscle" minWidth="190" />
				<el-table-column show-overflow-tooltip prop="heartAtrial" label="Heart_Atrial" minWidth="150" />
				<el-table-column show-overflow-tooltip prop="heartVentricle" label="Heart_Ventricle" minWidth="180" />
				<el-table-column show-overflow-tooltip prop="liver" label="Liver" minWidth="90" />
				<el-table-column show-overflow-tooltip prop="lung" label="Lung" minWidth="90" />

				<el-table-column show-overflow-tooltip prop="minorSalivary" label="Minor_Salivary" minWidth="150" />
				<el-table-column show-overflow-tooltip prop="muscleSkeletal" label="Muscle_Skeletal" minWidth="160" />
				<el-table-column show-overflow-tooltip prop="nerveTibial" label="Nerve_Tibial" minWidth="130" />
				<el-table-column show-overflow-tooltip prop="ovary" label="Ovary" minWidth="90" />
				<el-table-column show-overflow-tooltip prop="pancreas" label="Pancreas" minWidth="100" />
				<el-table-column show-overflow-tooltip prop="pituitary" label="Pituitary" minWidth="100" />
				<el-table-column show-overflow-tooltip prop="prostate" label="Prostate" minWidth="100" />
				<el-table-column show-overflow-tooltip prop="skinUnexpo" label="Skin_Unexpo" minWidth="130" />
				<el-table-column show-overflow-tooltip prop="skinSunexpo" label="Skin_SunExpo" minWidth="140" />
				<el-table-column show-overflow-tooltip prop="smallIntestine" label="Small_Intestine" minWidth="150" />
				<el-table-column show-overflow-tooltip prop="spleen" label="Spleen" minWidth="90" />
				<el-table-column show-overflow-tooltip prop="stomach" label="Stomach" minWidth="100" />
				<el-table-column show-overflow-tooltip prop="testis" label="Testis" minWidth="90" />
				<el-table-column show-overflow-tooltip prop="thyroid" label="Thyroid" minWidth="90" />
				<el-table-column show-overflow-tooltip prop="uterus" label="Uterus" minWidth="90" />
				<el-table-column show-overflow-tooltip prop="vagina" label="Vagina" minWidth="90" />
			</el-table>
			<img :src="picUrl(data.tsScore?.imgUrl)" alt="" srcset="" />
		</template> -->
	</div>
</template>

<style lang="scss" scoped>
@import url("@/styles/content.scss");
@import url("../index.scss");
</style>
