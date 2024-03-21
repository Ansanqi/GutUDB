<script setup>
const data = inject("data")
</script>

<template>
	<div class="my-table">
		<el-table :data="data.chemicalCompounds" border  size="default">
			<!-- <el-table-column show-overflow-tooltip prop="diseaseRelatedGenes" label="Disease related genes" minWidth="220" /> -->
			<el-table-column show-overflow-tooltip prop="disease" label="Disease" minWidth="200" />
			<el-table-column show-overflow-tooltip prop="species" label="Species" minWidth="100" />
			<el-table-column show-overflow-tooltip prop="directEvidence" label="Direct Evidence" minWidth="180" />
			<el-table-column show-overflow-tooltip prop="inferenceNetwork" label="Inference Network" minWidth="1000" />
			<el-table-column show-overflow-tooltip prop="source" label="Source" minWidth="100">
				<template #default="scope">
					<a class="table-link" :href="scope.row.sourceUrl" target="_blank">{{ scope.row.source }}</a>
				</template>
			</el-table-column>
			<el-table-column show-overflow-tooltip prop="referenceCount" label="Reference Count" minWidth="160" />
		</el-table>
	</div>
</template>

<style lang="scss" scoped>
@import url("@/styles/content.scss");
</style>
