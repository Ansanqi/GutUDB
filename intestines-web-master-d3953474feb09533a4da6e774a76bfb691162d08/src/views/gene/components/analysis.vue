<script setup>
const data = inject("data")
</script>

<template>
	<div class="my-table">
		<el-image :src="picUrl(data?.survivalAnalysis)" v-if="data?.survivalAnalysis">
			<template #error>
				<div class="image-slot">
					<el-empty description="No Data" />
				</div>
			</template>
		</el-image>
		<el-empty v-else />
	</div>
</template>

<style lang="scss" scoped>
@import url("@/styles/content.scss");
</style>
