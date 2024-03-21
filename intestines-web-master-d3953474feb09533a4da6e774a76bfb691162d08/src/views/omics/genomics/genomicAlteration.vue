<script setup>
const navList = ref(["CNA Genes", "Mutated Genes", "SNP", "Structural Variant Genes"])
// const navIndex = ref(sessionStorage.getItem("navIndex1") || 0)
const navIndex = ref(0)
const handleCurrentClick = (index) => {
	navIndex.value = index
	// sessionStorage.setItem("navIndex1", index)
}

import cnaGenes from "./genomicAlteration/cnaGenes.vue"
import mutatedGenes from "./genomicAlteration/mutatedGenes.vue"
import snp from "./genomicAlteration/snp.vue"
import structuralVariantGenes from "./genomicAlteration/structuralVariantGenes.vue"
const children = [cnaGenes, mutatedGenes, snp, structuralVariantGenes]
const componentName = computed(() => {
	return children[navIndex.value]
})
const sortChange = async ({ column, prop, order }) => {
	getList({ orderBy: `${prop}_${order}` })
}
</script>

<template>
	<div class="content">
		<Title />
		<div class="my-nav">
			<template v-for="(v, index) in navList" :key="v">
				<span :class="{ active: index == navIndex }" @click="handleCurrentClick(index)">{{ v }}</span>
			</template>
		</div>
		<component :is="componentName"></component>
	</div>
</template>

<style lang="scss" scoped>
@import url("@/styles/content.scss");
</style>
