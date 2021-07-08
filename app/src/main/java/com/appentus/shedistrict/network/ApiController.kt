package com.appentus.shedistrict.network


import UnsplashBean
import android.accessibilityservice.GestureDescription
import android.annotation.SuppressLint
import android.app.Activity
import android.app.Dialog
import android.content.Context
import com.appentus.shedistrict.models.BaseApiResponse
import com.appentus.shedistrict.models.UnsplaceSearchBean
import com.google.gson.Gson
import com.google.gson.JsonElement
import com.google.gson.reflect.TypeToken
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.json.JSONException
import retrofit2.http.Headers
import java.io.File
import java.lang.Exception


class ApiController(val context: Context, private val apiResponseListener: ResponseListener) {
     val loader: Dialog = ProgressView.getLoader(context )
    private val compositeDisposable = CompositeDisposable()

    /*with parm POST request*/
    @SuppressLint("CheckResult")
    fun makeCall(tag: String, params: MutableMap<String, String>?) {
        loader.show()
        val call = ApiClient.getClient(context).makeCall(AppConstant.BaseUrl + tag, params)
        call.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(object : Observer<JsonElement?> {
                    override fun onComplete() {
                        loader.dismiss()
                    }
                    override fun onSubscribe(d: Disposable) {
                        compositeDisposable.add(d)
                    }
                    override fun onNext(response: JsonElement) {
                        loader.dismiss()
                        val baseResponse =
                                Gson().fromJson<BaseApiResponse>(response, BaseApiResponse::class.java)
                        if (baseResponse.status == AppConstant.Success) {
                            try {
                                apiResponseListener.onSuccess(tag, response.toString())
                            } catch (e: JSONException) {
                                apiResponseListener.onFailure(tag, baseResponse.message)
                            }
                        } else {
                            apiResponseListener.onFailure(tag, baseResponse.message)
                        }

                    }
                    override fun onError(e: Throwable) {
                        loader.dismiss()
                        if (e is NoConnectivityException) {
                            apiResponseListener.onNoConnection(tag, "No Internet Connection")
                        } else {
                            apiResponseListener.onError(tag, e.message.toString())
                        }
                    }
                })


    }

    @SuppressLint("CheckResult")
    fun makeCalls(tag: String, params: MutableMap<String, String>?) {
        val call = ApiClient.getClient(context).makeCall(AppConstant.BaseUrl + tag, params)
        call.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(object : Observer<JsonElement?> {
                    override fun onComplete() {
                        loader.dismiss()
                    }
                    override fun onSubscribe(d: Disposable) {
                        compositeDisposable.add(d)
                    }
                    override fun onNext(response: JsonElement) {
                        val baseResponse =
                                Gson().fromJson<BaseApiResponse>(response, BaseApiResponse::class.java)
                        if (baseResponse.status == AppConstant.Success) {
                            try {
                                apiResponseListener.onSuccess(tag, response.toString())
                            } catch (e: JSONException) {
                                apiResponseListener.onFailure(tag, baseResponse.message)
                            }
                        } else {
                            apiResponseListener.onFailure(tag, baseResponse.message)
                        }

                    }
                    override fun onError(e: Throwable) {
                        if (e is NoConnectivityException) {
                            apiResponseListener.onNoConnection(tag, "No Internet Connection")
                        } else {
                            apiResponseListener.onError(tag, e.message.toString())
                        }
                    }
                })


    }

    /*with parm GET request*/
    @SuppressLint("CheckResult")
    fun makeCallGET(tag: String) {
        loader.show()
        val call = ApiClient.getClient(context).makeCallGet(AppConstant.BaseUrl + tag)
        call.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(object : Observer<JsonElement?> {
                    override fun onComplete() {
                    }
                    override fun onSubscribe(d: Disposable) {
                        compositeDisposable.add(d)
                    }
                    override fun onNext(response: JsonElement) {
                        loader.dismiss()
                        val BaseResponse =
                                Gson().fromJson<BaseApiResponse>(response, BaseApiResponse::class.java)
                        if (BaseResponse.status == AppConstant.Success) {
                            try {
                                apiResponseListener.onSuccess(tag, response.toString())
                            } catch (e: JSONException) {
                                apiResponseListener.onFailure(tag, BaseResponse.message)
                            }
                        } else {
                            apiResponseListener.onFailure(tag, BaseResponse.message)
                        }
                    }
                    override fun onError(e: Throwable) {
                        if (e is NoConnectivityException) {
                            apiResponseListener.onNoConnection(tag, "No Internet Connection")
                        } else {
                            apiResponseListener.onError(tag, e.message.toString())
                        }
                    }
                })
    }

    @SuppressLint("CheckResult")
    fun makeCallGETwithoutload(tag: String) {

        val call = ApiClient.getClient(context).makeCallGet(AppConstant.BaseUrl + tag)
        call.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(object : Observer<JsonElement?> {
                    override fun onComplete() {
                    }
                    override fun onSubscribe(d: Disposable) {
                        compositeDisposable.add(d)
                    }
                    override fun onNext(response: JsonElement) {

                        val BaseResponse =
                                Gson().fromJson<BaseApiResponse>(response, BaseApiResponse::class.java)
                        if (BaseResponse.status == AppConstant.Success) {
                            try {
                                apiResponseListener.onSuccess(tag, response.toString())
                            } catch (e: JSONException) {
                                apiResponseListener.onFailure(tag, BaseResponse.message)
                            }
                        } else {
                            apiResponseListener.onFailure(tag, BaseResponse.message)
                        }
                    }
                    override fun onError(e: Throwable) {
                        if (e is NoConnectivityException) {
                            apiResponseListener.onNoConnection(tag, "No Internet Connection")
                        } else {
                            apiResponseListener.onError(tag, e.message.toString())
                        }
                    }
                })
    }


    /*with parm POST request in background*/
    @SuppressLint("CheckResult")
    fun makeCallSilent(tag: String, params: MutableMap<String, String>) {
        val call = ApiClient.getClient(context).makeCall(AppConstant.BaseUrl + tag, params)
        call.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(object : Observer<JsonElement?> {
                    override fun onComplete() {
                    }
                    override fun onSubscribe(d: Disposable) {
                        compositeDisposable.add(d)
                    }
                    override fun onNext(response: JsonElement) {
                        val BaseResponse =
                                Gson().fromJson<BaseApiResponse>(response, BaseApiResponse::class.java)
                        if (BaseResponse.status == AppConstant.Success) {
                            apiResponseListener.onSuccess(tag, response.toString())
                        } else {
                            apiResponseListener.onFailure(tag, BaseResponse.message)
                        }
                    }
                    override fun onError(e: Throwable) {
                        if (e is NoConnectivityException) {
                            apiResponseListener.onNoConnection(tag, "No Internet Connection")
                        } else {
                            apiResponseListener.onError(tag, e.message.toString())
                        }
                    }
                })
    }
    fun unSubscribe() {
        compositeDisposable.dispose()
    }

    /*with parm POST MULTIPART request for Single image file*/
    @SuppressLint("CheckResult")
    fun makeCall(tag: String, params: MultipartBody) {
        loader.show()
        val call = ApiClient.getClient(context).makeCall(AppConstant.BaseUrl + tag, params)
        call.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(object : Observer<JsonElement?> {
                    override fun onComplete() {

                    }
                    override fun onSubscribe(d: Disposable) {

                        compositeDisposable.add(d)
                    }
                    override fun onNext(response: JsonElement) {
                        loader.dismiss()
                        val BaseResponse =
                                Gson().fromJson<BaseApiResponse>(response, BaseApiResponse::class.java)
                        if (BaseResponse.status == AppConstant.Success) {
                            apiResponseListener.onSuccess(tag, response.toString())
                        } else {
                            apiResponseListener.onFailure(tag, BaseResponse.message)
                        }
                    }
                    override fun onError(e: Throwable) {

                        if (e is NoConnectivityException) {
                            apiResponseListener.onNoConnection(tag, "No Internet Connection")
                        } else {
                            apiResponseListener.onError(tag, e.message.toString())
                        }
                    }
                })
    }

    /*with parm POST MULTIPART request for multiple image list*/
    @SuppressLint("CheckResult")
    fun makeCall(tag: String, params: MutableMap<String, String>?, files: MutableList<File>?) {
        loader.show()
        val builder = MultipartBody.Builder()
        builder.setType(MultipartBody.FORM)
        if (params != null) {
            for ((key, value) in params) {
                builder.addFormDataPart(key, value)
            }
        }
        if (!files.isNullOrEmpty()) {
            for (i in files.indices) {
                builder.addFormDataPart(
                        "photos[]",
                        files[i]?.name, RequestBody.create(MediaType.parse("multipart/form-data"),files[i]!!)
                       // files[i].name, RequestBody.create(MediaType.parse("multipart/form-data"), files[i].name)
                )
            }
        }
        val call = ApiClient.getClient(context).makeCall(AppConstant.BaseUrl + tag, builder.build())
        call.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(object : Observer<JsonElement?> {
                    override fun onComplete() {
                    }
                    override fun onSubscribe(d: Disposable) {
                        compositeDisposable.add(d)
                    }
                    override fun onNext(response: JsonElement) {
                        loader.dismiss()
                        try {
                            val BaseResponse =
                                    Gson().fromJson<BaseApiResponse>(response, BaseApiResponse::class.java)
                            if (BaseResponse.status == AppConstant.Success) {
                                apiResponseListener.onSuccess(tag, response.toString())
                            } else {
                                apiResponseListener.onFailure(tag, BaseResponse.message)
                            }
                        }
                        catch(e:Exception){
                            e.printStackTrace()
                        }
                    }
                    override fun onError(e: Throwable) {
                        loader.dismiss()
                        if (e is NoConnectivityException) {
                            apiResponseListener.onNoConnection(tag, "No Internet Connection")
                        } else {
                            apiResponseListener.onError(tag, e.message.toString())
                        }
                    }
                })
    }

    fun <T> parseJson(jsonString: String, modelClass: Class<T>): T {
        val gson = Gson()
        return gson.fromJson(jsonString, modelClass)
    }


    @SuppressLint("CheckResult")
    fun unSplashApi(tag: String,page:String ) {
        loader.show()
        val call = ApiClient.getClient(context).unsplashApi(page)
        call.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(object : Observer<JsonElement?> {
                    override fun onComplete() {
                    }
                    override fun onSubscribe(d: Disposable) {
                        compositeDisposable.add(d)
                    }
                    override fun onNext(response: JsonElement) {
                        loader.dismiss()
                        var listType = object : TypeToken<List<UnsplashBean>>() {}.type
                        var projects = Gson().fromJson(response, listType) as List<UnsplashBean>

                        if(projects!=null&&projects.isNotEmpty()){
                            apiResponseListener.onSuccess(tag, response.toString())
                        }else{
                            apiResponseListener.onFailure(tag, "Api Error")
                        }
                    }
                    override fun onError(e: Throwable) {
                        if (e is NoConnectivityException) {
                            apiResponseListener.onNoConnection(tag, "No Internet Connection")
                        } else {
                            apiResponseListener.onError(tag, e.message.toString())
                        }
                    }
                })
    }

    @SuppressLint("CheckResult")
    fun unSplashSerchApi(tag: String,page:String,query:String ) {
        loader.show()
        val call = ApiClient.getClient(context).unsplashSearchApi(page,query)
        call.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(object : Observer<JsonElement?> {
                    override fun onComplete() {
                    }
                    override fun onSubscribe(d: Disposable) {
                        compositeDisposable.add(d)
                    }
                    override fun onNext(response: JsonElement) {
                        loader.dismiss()
                     /*   var listType = object : TypeToken<List<UnsplaceSearchBean.ResultsBean>>() {}.type
                        var projects = Gson().fromJson(response, listType) as List<UnsplaceSearchBean.ResultsBean>

                        if(projects!=null&&projects.isNotEmpty()){
                            apiResponseListener.onSuccess(tag, response.toString())
                        }else{
                            apiResponseListener.onFailure(tag, "Api Error")
                        }*/
                        try {

                            val unsplaceSearchBean =
                                    Gson().fromJson(response, UnsplaceSearchBean::class.java)
                            if (unsplaceSearchBean!= null) {
                                apiResponseListener.onSuccess(tag, response.toString())
                            } else {
                                apiResponseListener.onFailure(tag, "Somthing went wrong")
                            }
                        }
                        catch(e:Exception){
                            e.printStackTrace()
                        }
                    }
                    override fun onError(e: Throwable) {
                        if (e is NoConnectivityException) {
                            apiResponseListener.onNoConnection(tag, "No Internet Connection")
                        } else {
                            apiResponseListener.onError(tag, e.message.toString())
                        }
                    }
                })
    }

    @SuppressLint("CheckResult")
    fun downlload(tag: String) {
        loader.show()
        val call = ApiClient.getClient(context).makeCallGet(AppConstant.BaseUrl + tag)
        call.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(object : Observer<JsonElement?> {
                    override fun onComplete() {
                    }
                    override fun onSubscribe(d: Disposable) {
                        compositeDisposable.add(d)
                    }
                    override fun onNext(response: JsonElement) {
                        loader.dismiss()
                        val BaseResponse =
                                Gson().fromJson<BaseApiResponse>(response, BaseApiResponse::class.java)
                        if (BaseResponse.status == AppConstant.Success) {
                            try {
                                apiResponseListener.onSuccess(tag, response.toString())
                            } catch (e: JSONException) {
                                apiResponseListener.onFailure(tag, BaseResponse.message)
                            }
                        } else {
                            apiResponseListener.onFailure(tag, BaseResponse.message)
                        }
                    }
                    override fun onError(e: Throwable) {
                        if (e is NoConnectivityException) {
                            apiResponseListener.onNoConnection(tag, "No Internet Connection")
                        } else {
                            apiResponseListener.onError(tag, e.message.toString())
                        }
                    }
                })
    }



}
