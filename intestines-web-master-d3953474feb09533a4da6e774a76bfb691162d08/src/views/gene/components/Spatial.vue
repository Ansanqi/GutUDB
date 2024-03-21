<script setup>
const data = inject("data")
</script>

<template>
	<div class="my-table">
		<el-table :data="data.spatialHistologies" border  size="default">
			<el-table-column show-overflow-tooltip prop="species" label="Species" minWidth="220" />
			<el-table-column show-overflow-tooltip prop="tissue" label="Tissue" minWidth="220" />
			<el-table-column show-overflow-tooltip prop="biotechCategories" label="Biotech Categories" minWidth="220" />
			<el-table-column show-overflow-tooltip prop="expressionRange" label="Expression Range" minWidth="220" />
			<el-table-column show-overflow-tooltip prop="biotech" label="Biotech" minWidth="220" />
			<el-table-column show-overflow-tooltip prop="nunit" label="N Unit" minWidth="220" />
			<el-table-column show-overflow-tooltip prop="title" label="Title" minWidth="220" />
		</el-table>
	</div>
</template>

<style lang="scss" scoped>
@import url("@/styles/content.scss");
</style>
