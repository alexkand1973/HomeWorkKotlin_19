package com.alexkand.homeworkkotlin_19

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Maybe
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.concurrent.TimeUnit

//Сделайте несколько разных Observable (Например: Observable, Flowable, Single и так далее).
// Подпишитесь на данные observable и пропишите какие-либо действия
// в соответствуюих для них функциях.

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Observable.just(10, 20, 30, 40, 50, 60, 70)
            .subscribeOn(Schedulers.computation())
            //.observeOn(AndroidSchedulers.mainThread()) //какойто поток из пула переключили поток из io на Main
            .map { number -> number + 10}
            .filter { number -> number > 20 }
            .firstElement()
            //.delay(100, TimeUnit.MILLISECONDS)
            .subscribe ({ value: Int ->
                println("It's number in the thread $value, ${Thread.currentThread()}")},
                { error -> println("It's error $error") },
                { println("The program over") })

        Maybe.just(7)
            .subscribeOn(Schedulers.newThread())
            .subscribe({ number ->
                println("It's $number")},
                { error ->
                    println(error)
                }, { println("It's not printed") })

        Single.just("Hello Java!")
            .subscribeOn(Schedulers.io())
            .subscribe({string ->
                println("$string")
            }, { println(" It's not work") })

        Flowable.just(5, 10, 15, 20, 25, 30, 35)
            .subscribeOn(Schedulers.io())
            .map { number -> number * 3}
            .delay(2, TimeUnit.SECONDS)
            .filter { number -> number > 15 }
            .firstElement()
            .subscribe ({ value: Int ->
                println("It's number in the thread $value, ${Thread.currentThread()}")},
                { error -> println("It's error $error") },
                { println("The End") })
    }
}