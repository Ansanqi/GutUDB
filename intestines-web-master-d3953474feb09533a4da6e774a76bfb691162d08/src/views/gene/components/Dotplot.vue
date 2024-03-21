<script setup>
const data = inject("data")

const imgUrl = ref({
	boxplotImgUrl: "",
	dotplotImgUrl: "",
})
const cellMouseEnter = (row) => {
	imgUrl.value.boxplotImgUrl = row.boxplotImgUrl
	imgUrl.value.dotplotImgUrl = row.dotplotImgUrl
}

onUpdated(() => {
	cellMouseEnter(data.value.boxlotDotplots[0])
})
</script>

<template>
	<div class="my-table">
		<div class="tips">Gene expression data</div>
		<el-table :data="data.boxlotDotplots" border size="default" @cell-mouse-enter="cellMouseEnter">
			<!-- <el-table-column show-overflow-tooltip label="Gene Name" minWidth="110">
					<template #default="scope">
						<router-link :to="{ path: '/gene/index', query: { geneName: scope.row.geneName } }" class="table-link">{{ scope.row.geneName }}</router-link>
					</template>
				</el-table-column> -->
			<el-table-column show-overflow-tooltip prop="ensembleId" label="Ensemble ID" minWidth="220" />
			<el-table-column show-overflow-tooltip prop="meanCase" label="Mean (Case)" minWidth="220" >
				<template #default="scope">{{ parseFloat(scope.row.meanCase).toFixed(4) }}</template>
			</el-table-column>
			<el-table-column show-overflow-tooltip prop="meanControl" label="Mean (Control)" minWidth="220" >
				<template #default="scope">{{ parseFloat(scope.row.meanControl).toFixed(4) }}</template>
			</el-table-column>
			<el-table-column show-overflow-tooltip prop="log2FoldChange" label="Log2(Fold Change)" minWidth="220" >
				<template #default="scope">{{ parseFloat(scope.row.log2FoldChange).toFixed(4) }}</template>
			</el-table-column>
			<el-table-column show-overflow-tooltip prop="pvalue" label="p.value" minWidth="220" >
				<template #default="scope">{{ parseFloat(scope.row.pvalue).toFixed(4) }}</template>
			</el-table-column>
		</el-table>
		<div class="imgs">
			<el-image :src="picUrl(imgUrl.boxplotImgUrl)" v-if="imgUrl.boxplotImgUrl">
				<template #error>
					<div class="image-slot">
						<el-empty description="No Data" />
					</div>
				</template>
			</el-image>
			<el-empty v-else />
			<el-image :src="picUrl(imgUrl.dotplotImgUrl)" v-if="imgUrl.dotplotImgUrl">
				<template #error>
					<div class="image-slot">
						<el-empty description="No Data" />
					</div>
				</template>
			</el-image>
			<el-empty v-else />
		</div>
	</div>
</template>

<style lang="scss" scoped>
@import url("@/styles/content.scss");
@import url("../index.scss");
</style>
