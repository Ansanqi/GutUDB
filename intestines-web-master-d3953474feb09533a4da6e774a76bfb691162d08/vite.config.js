import vue from "@vitejs/plugin-vue"

import Components from "unplugin-vue-components/vite"
import AutoImport from "unplugin-auto-import/vite"
import { ElementPlusResolver } from 'unplugin-vue-components/resolvers'

import Icons from 'unplugin-icons/vite'
import IconsResolver from 'unplugin-icons/resolver'

import { join } from "path"


export default {
	base: './',
	resolve: {
		alias: {
			"@": join(__dirname, "./src"),
		}
	},
	// 反向代理
	server: {
		host: "0.0.0.0",
		port: '20100',
		open: true,
		cors: true,
		proxy: {
			'/intestinedb': {
				target: 'http://47.109.69.209:8314',
				changeOrigin: true,
				rewrite: (path) => path.replace(/^\/api/, ''),
			},
		}
	},
	plugins: [
		vue(),
		Components({
			dirs: ["src/components"],
			resolvers: [
				// 自动注册图标组件
        IconsResolver({
					prefix: false,
          enabledCollections: ['ep'],
        }),
				// 自动导入 Element Plus 组件
				ElementPlusResolver({ importStyle: "sass" })
			],
		}),
		AutoImport({
			imports: [
				"vue",
				"vue-router",
				{
					"@/utils/request": ["api"],
					"@/utils/getImageSrc": ["getSrc", "picUrl"],
				},
			],
			vueTemplate: true,
			dts: false,
			resolvers: [
				ElementPlusResolver(),
			],
		}),
		Icons({
      autoInstall: true,
			compiler: "vue3",
    }),
	],
	css: {
		preprocessorOptions: {
			scss: {
				additionalData: `
					@use "@/styles/element/index.scss" as *;
					@use "@/styles/var.scss" as *;
					`,
			},
		},
	},
}
