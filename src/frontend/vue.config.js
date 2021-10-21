// vue.config.js

module.exports = {
    devServer: {
        port: 3000,
        proxy: {
            '/api': {
                target: 'https://springVueConference.herokuapp.com/',
                ws: true,
                changeOrigin: true
            }
        }
    }
};