<script setup>
const navList = ref(["circRNA", "lnc RNA", "miRNA"])
// const navIndex = ref(sessionStorage.getItem("navIndex3") || 0)
const navIndex = ref(0)
const handleCurrentClick = (index) => {
	navIndex.value = index
	// sessionStorage.setItem("navIndex3", index)
}

import circRNA from './nonCodingRNA/circRNA.vue';
import incRNA from './nonCodingRNA/incRNA.vue';
import miRNA from './nonCodingRNA/miRNA.vue';
const children = [circRNA,incRNA,miRNA]
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
