<script setup>
const data = inject("data")
</script>

<template>
	<div class="my-table">
		<el-table :data="data.metabolomicsList" border  size="default">
			<el-table-column show-overflow-tooltip prop="disease" label="Disease" minWidth="220" />
			<el-table-column show-overflow-tooltip prop="gutMicrobiota" label="Gut Microbiota" minWidth="220" />
			<el-table-column show-overflow-tooltip prop="metabolite" label="Metabolite" minWidth="220" />
			<el-table-column show-overflow-tooltip prop="species" label="Species" minWidth="220" />
			<el-table-column show-overflow-tooltip prop="classification" label="Classification" minWidth="220" />
			<el-table-column show-overflow-tooltip prop="description" label="Description" minWidth="220" />
			<el-table-column show-overflow-tooltip prop="sampleType" label="Sample Type" minWidth="220" />
			<el-table-column show-overflow-tooltip prop="substrate" label="Substrate" minWidth="220" />
			<el-table-column show-overflow-tooltip prop="alteration" label="Alteration" minWidth="220" />
			<el-table-column show-overflow-tooltip prop="experimentalMethod" label="Experimental Method" minWidth="220" />
			<el-table-column show-overflow-tooltip prop="measurementTechnique" label="Measurement Technique" minWidth="230" />
			<el-table-column show-overflow-tooltip prop="geneAssociation" label="Gene Association" minWidth="300">
				<template #default="scope">
					<span v-if="scope.row.geneAssociation === 'NA' || !scope.row.geneAssociation">NA</span>
					<a v-else class="table-link" :href="scope.row.geneAssociation" target="_blank">{{ scope.row.geneAssociation }}</a>
				</template>
			</el-table-column>
			<el-table-column show-overflow-tooltip prop="pmidAssociation" label="PMID Association" minWidth="300">
				<template #default="scope">
					<span v-if="scope.row.pmidAssociation === 'NA' || !scope.row.pmidAssociation">NA</span>
					<a v-else class="table-link" :href="scope.row.pmidAssociation" target="_blank">{{ scope.row.pmidAssociation }}</a>
				</template>
			</el-table-column>
		</el-table>
	</div>
</template>

<style lang="scss" scoped>
@import url("@/styles/content.scss");
</style>
