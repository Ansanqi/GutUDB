<script setup>
const navList = ref(["SraRun", "A3SS.MATS.JCEC", "A5SS.MATS.JCEC", "MXE.MATS.JCEC", "RI.MATS.JCEC", "SE.MATS.JCEC"])
// const navIndex = ref(sessionStorage.getItem("navIndex2") || 0)
const navIndex = ref(0)
const handleCurrentClick = (index) => {
	navIndex.value = index
	// sessionStorage.setItem("navIndex2", index)
}

import sraRun from "./dataAccess/sraRun.vue"
import a3SS from "./dataAccess/a3SS.vue"
import a5SS from "./dataAccess/a5SS.vue"
import mXE from "./dataAccess/mXE.vue"
import rI from "./dataAccess/rI.vue"
import sE from "./dataAccess/sE.vue"
const children = [sraRun, a3SS, a5SS, mXE, rI, sE]
const componentName = computed(() => {
	return children[navIndex.value]
})
const sortChange = async ({ column, prop, order }) => {
	getList({ orderBy: `${prop}_${order}` })
}
</script>

<template>
	<div class="content">
		<Title :isBack="true" />
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
