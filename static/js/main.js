import { createApp, nextTick, reactive } from "https://unpkg.com/petite-vue?module"

function request(url, opts = {}) {
    return new Promise((res, rej) => {
        let xhr = new XMLHttpRequest()
        xhr.open(opts?.method || "GET", url, true)
        if (opts?.headers) {
            for (let i in opts.headers) {
                xhr.setRequestHeader(i, opts.headers[i])
            }
        }
        xhr.onloadend = xhr.onerror = () => {
            if (xhr.readyState === 4 && xhr.status >= 200 && xhr.status < 400) {
                res(xhr)
            } else {
                rej(xhr)
            }
        }
        xhr.send(opts?.body)
    })
}
function getCookie(name) {
    let matches = document.cookie.match(new RegExp(
        "(?:^| )" + name.replace(/([\.$?*|{}\(\)\[\]\\\/\+^])/g, '\\$1') + "=([^]*)"
    ))
    return matches ? decodeURIComponent(matches[1]) : undefined
}
function setCookie(name, value, options = {}) {
    options = {
        path: '/',
        ...options
    }
    if (options.expires instanceof Date) {
        options.expires = options.expires.toUTCString()
    }
    let updatedCookie = encodeURIComponent(name) + "=" + encodeURIComponent(value)
    for (let optionKey in options) {
        updatedCookie += " " + optionKey
        let optionValue = options[optionKey]
        if (optionValue !== true) {
            updatedCookie += "=" + optionValue
        }
    }
    document.cookie = updatedCookie
}

function getF(f, params){
    let fs = {
        Authors(){
            return{
                '$template':"#AuthorsPage",
                params: params,
                books: [],
                authors: [],
                one: false,
                mounted(){
                    request(this.params["id"] ? `/api/authors?id=${this.params["id"]}` : `/api/authors`).then(d => {
                        if(d.status != 200){
                            console.log(d)
                            return
                        }
        
                        d = JSON.parse(d.responseText)
        
                        this.one = this.params["id"] ? true : false

                        if( this.params["id"] ){
                            this.books = d.books
                            this.authors.push(d)
                        }else{
                            this.authors = d
                        }
        
                    }).catch(e => {
                        console.log(e)
                    })
                }
            }
        },
        Books(){
            return{
                '$template':"#BooksPage",
                params: params,
                books: [],
                one: false,
                mounted(){
                    request(this.params["id"] ? `/api/books?id=${this.params["id"]}` : `/api/books`).then(d => {
                        if(d.status != 200){
                            console.log(d)
                            return
                        }
        
                        d = JSON.parse(d.responseText)
        
                        this.one = this.params["id"] ? true : false

                        if( this.params["id"] ){
                            this.books.push(d)
                        }else{
                            this.books = d
                        }
        
                    }).catch(e => {
                        console.log(e)
                    })
                }
            }
        },
        Main(){
            return{
                '$template':"#MainPage",
                params: params,
                books: [],
                mounted(){
                    request("/api/books?action=last").then(d => {
                        if(d.status != 200){
                            console.log(d)
                            return
                        }
        
                        d = JSON.parse(d.responseText)
        
                        this.books = d
        
                    }).catch(e => {
                        console.log(e)
                    })
                }
            }
        }
    }

    return fs[f]
}

let router = {
    routes: {
        "/authors": "Authors",
        "/books": "Books",
        "/": "Main"
    },
    init(){
        let route = ""
        for ( let i in this.routes){
            if( document.location.hash.indexOf(i) > -1 ){
                route = this.routes[i]
                break
            }
        }
        let page = getF(route, this.getParams())
        createApp({
            page
        }).mount()
    },
    getParams(){
        let out = {}

        let qs = document.location.hash.split("?")

        if(qs.length < 2) return {}

        qs[1].split("&").forEach(el => {
            let t = el.split("=")
            out[t[0]] = t[1]
        })

        return out
    }
}

router.init()

window.onpopstate = e => {
    document.location.reload()
}