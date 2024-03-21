<script setup>
const data = inject("data")
</script>

<template>
	<div class="my-table">
		<el-table :data="data.chineseMedicines" border  size="default">
			<el-table-column show-overflow-tooltip prop="disease" label="Disease" minWidth="220" />
			<el-table-column show-overflow-tooltip prop="diseaseRelatedGenes" label="Disease related genes" minWidth="800" />
			<el-table-column show-overflow-tooltip prop="herbsAssociatedWithThisDisease" label="Herbs Associated with This Disease" minWidth="320">
				<template #default="scope">
					<a class="table-link" :href="scope.row.herbsAssociatedWithThisDiseaseUrl" target="_blank">{{ scope.row.herbsAssociatedWithThisDisease }}</a>
				</template>
			</el-table-column>
		</el-table>
	</div>
</template>

<style lang="scss" scoped>
@import url("@/styles/content.scss");
</style>
