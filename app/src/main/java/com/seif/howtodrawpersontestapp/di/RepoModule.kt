package com.seif.howtodrawpersontestapp.di

import android.content.Context
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.seif.howtodrawpersontestapp.data.repository.ChildRepositoryImpl
import com.seif.howtodrawpersontestapp.domain.repository.ChildRepository
import com.seif.howtodrawpersontestapp.util.SharedPrefs
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object RepoModule {
    @Singleton
    @Provides
    fun provideFirestoreInstance(): FirebaseFirestore {
        return FirebaseFirestore.getInstance()
    }

    @Singleton
    @Provides
    fun provideStorageInstance(): FirebaseStorage {
        return FirebaseStorage.getInstance()
    }
    @Provides
    @Singleton
    fun provideSharedPref(@ApplicationContext context: Context): SharedPrefs {
        return SharedPrefs(context)
    }
    @Provides
    @Singleton
    fun provideChildRepository(
        firestore: FirebaseFirestore,
        sharedPrefs: SharedPrefs,
        storage: FirebaseStorage
    ): ChildRepository {
        return ChildRepositoryImpl(
            firestore,
            storage,
            sharedPrefs
        )
    }
}
