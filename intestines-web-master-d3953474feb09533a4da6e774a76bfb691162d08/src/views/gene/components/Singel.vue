<script setup>
const props = defineProps({
	id: String,
})
const data = inject("data")
const navList = ["Gene expression", "Alternative splicing", "Proteomics"]
const navIndex = ref(0)
</script>

<template>
	<div class="my-table">
		<template v-for="(v, index) in navList">
			<el-tabs v-model="navList[index]" :id="props.id + '-' + index">
				<el-tab-pane :label="v" :name="v">
					<template v-if="index === 0">
						<el-table :data="data.singleCellGeneExpressData" border size="default">
							<el-table-column show-overflow-tooltip prop="ensembleId" label="Ensemble ID" minWidth="180" />
							<el-table-column show-overflow-tooltip prop="name" label="Name" minWidth="330" />
							<el-table-column show-overflow-tooltip prop="cancerType" label="Cancer Type" minWidth="330" />
							<el-table-column show-overflow-tooltip prop="tissue" label="Tissue" minWidth="120" />
							<el-table-column show-overflow-tooltip prop="treatmentType" label="Treatment Type" minWidth="160" />
							<el-table-column show-overflow-tooltip prop="uniqueId" label="Unique Id" minWidth="250" />
							<el-table-column show-overflow-tooltip prop="cluster" label="Cluster" minWidth="100" />
							<el-table-column show-overflow-tooltip prop="enrichmentCellTypes" label="Enrichment Cell Types" minWidth="210" />
							<el-table-column show-overflow-tooltip prop="cellAmount" label="Cell amount" minWidth="130" />
							<el-table-column show-overflow-tooltip prop="pval" label="p_val" minWidth="80" />
							<el-table-column show-overflow-tooltip prop="avgLog2Fc" label="avg_log2FC" minWidth="120" />
							<el-table-column show-overflow-tooltip prop="pct1" label="pct.1" minWidth="80" />
							<el-table-column show-overflow-tooltip prop="pct2" label="pct.2" minWidth="80" />
							<el-table-column show-overflow-tooltip prop="pvalAdj" label="p_val_adj" minWidth="100" />
							<el-table-column show-overflow-tooltip prop="title" label="Title" minWidth="400" />
						</el-table>
					</template>
					<template v-else-if="index === 1">
						<el-table :data="data.alternativeSplicings" border size="default">
							<el-table-column show-overflow-tooltip prop="cluster1" label="Cluster1" minWidth="140" />
							<el-table-column show-overflow-tooltip prop="cluster2" label="Cluster2" minWidth="130" />
							<el-table-column show-overflow-tooltip prop="junction" label="Junction" minWidth="230" />
							<el-table-column show-overflow-tooltip prop="logFc" label="logFC" minWidth="170" />
							<el-table-column show-overflow-tooltip prop="adjPVal" label="adj.P.Val" minWidth="190" />
						</el-table>
					</template>
				</el-tab-pane>
			</el-tabs>
		</template>
		<!-- <div class="button_wrap">
			<template v-for="(v, index) in navList" :key="v">
				<div class="button" :class="{ active: index === navIndex }" @click="navIndex = index">{{ v }}</div>
			</template>
		</div>
		<template v-if="navIndex === 0">
			<el-table :data="data.singleCellGeneExpressData" border size="default">
				<el-table-column show-overflow-tooltip prop="ensembleId" label="Ensemble ID" minWidth="180" />
				<el-table-column show-overflow-tooltip label="Gene Name" minWidth="110">
					<template #default="scope">
						<router-link :to="{ path: '/gene/index', query: { geneName: scope.row.geneName } }" class="table-link">{{ scope.row.geneName }}</router-link>
					</template>
				</el-table-column>
				<el-table-column show-overflow-tooltip prop="name" label="Name" minWidth="330" />
				<el-table-column show-overflow-tooltip prop="cancerType" label="Cancer Type" minWidth="330" />
				<el-table-column show-overflow-tooltip prop="tissue" label="Tissue" minWidth="120" />
				<el-table-column show-overflow-tooltip prop="treatmentType" label="Treatment Type" minWidth="160" />
				<el-table-column show-overflow-tooltip prop="uniqueId" label="Unique Id" minWidth="250" />
				<el-table-column show-overflow-tooltip prop="cluster" label="Cluster" minWidth="100" />
				<el-table-column show-overflow-tooltip prop="enrichmentCellTypes" label="Enrichment Cell Types" minWidth="210" />
				<el-table-column show-overflow-tooltip prop="cellAmount" label="Cell amount" minWidth="130" />
				<el-table-column show-overflow-tooltip prop="pval" label="p_val" minWidth="80" />
				<el-table-column show-overflow-tooltip prop="avgLog2Fc" label="avg_log2FC" minWidth="120" />
				<el-table-column show-overflow-tooltip prop="pct1" label="pct.1" minWidth="80" />
				<el-table-column show-overflow-tooltip prop="pct2" label="pct.2" minWidth="80" />
				<el-table-column show-overflow-tooltip prop="pvalAdj" label="p_val_adj" minWidth="100" />
				<el-table-column show-overflow-tooltip prop="title" label="Title" minWidth="400" />
			</el-table>
		</template>
		<template v-else-if="navIndex === 1">
			<el-table :data="data.alternativeSplicings" border size="default">
				<el-table-column show-overflow-tooltip label="Gene Name" minWidth="110">
					<template #default="scope">
						<router-link :to="{ path: '/gene/index', query: { geneName: scope.row.geneName } }" class="table-link">{{ scope.row.geneName }}</router-link>
					</template>
				</el-table-column>
				<el-table-column show-overflow-tooltip prop="cluster1" label="Cluster1" minWidth="140" />
				<el-table-column show-overflow-tooltip prop="cluster2" label="Cluster2" minWidth="130" />
				<el-table-column show-overflow-tooltip prop="junction" label="Junction" minWidth="230" />
				<el-table-column show-overflow-tooltip prop="logFc" label="logFC" minWidth="170" />
				<el-table-column show-overflow-tooltip prop="adjPVal" label="adj.P.Val" minWidth="190" />
			</el-table>
		</template>
		<template v-else>
			<el-table :data="data.proteomics" border stripe size="default">
				<el-table-column show-overflow-tooltip type="expand" label="Details" width="90" />
				<el-table-column show-overflow-tooltip prop="geneName" label="Gene Name" minWidth="120" />
				<el-table-column show-overflow-tooltip prop="x-47x9" label="47x9" minWidth="80" />
				<el-table-column show-overflow-tooltip prop="x-34x45" label="34x45" minWidth="80" />
				<el-table-column show-overflow-tooltip prop="x-33x39" label="33x39" minWidth="80" />
				<el-table-column show-overflow-tooltip prop="x-41x30" label="41x30" minWidth="80" />
				<el-table-column show-overflow-tooltip prop="x-2x22" label="2x22" minWidth="80" />
				<el-table-column show-overflow-tooltip prop="x-22x40" label="22x40" minWidth="80" />
				<el-table-column show-overflow-tooltip prop="x-8x8" label="8x8" minWidth="80" />
				<el-table-column show-overflow-tooltip prop="x-36x43" label="36x43" minWidth="80" />
				<el-table-column show-overflow-tooltip prop="x-18x39" label="18x39" minWidth="80" />
				<el-table-column show-overflow-tooltip prop="x-29x30" label="29x30" minWidth="80" />
				<el-table-column show-overflow-tooltip prop="x-14x5" label="14x5" minWidth="80" />
				<el-table-column show-overflow-tooltip prop="x-8x9" label="8x9" minWidth="80" />
				<el-table-column show-overflow-tooltip prop="x-10x28" label="10x28" minWidth="80" />
			</el-table>
		</template> -->
	</div>
</template>

<style lang="scss" scoped>
@import url("@/styles/content.scss");
@import url("../index.scss");
</style>
