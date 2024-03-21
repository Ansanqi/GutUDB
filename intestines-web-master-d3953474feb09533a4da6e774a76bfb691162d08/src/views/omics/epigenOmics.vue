<script setup>const sortChange = async ({ column, prop, order }) => {
	getList({ orderBy: `${prop}_${order}` })
}
</script>

<template>
	<div class="content">
		<Title />
		epigenOmics
	</div>
</template>

<style lang="scss" scoped></style>
