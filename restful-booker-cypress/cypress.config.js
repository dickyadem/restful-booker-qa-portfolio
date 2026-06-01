const { defineConfig } = require('cypress')

module.exports = defineConfig({
   experimentalStudio: true,
  e2e: {
    baseUrl: 'https://automationintesting.online',
    viewportWidth: 1280,
    viewportHeight: 720,
    defaultCommandTimeout: 10000,
    video: false,
  },
})