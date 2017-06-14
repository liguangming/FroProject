package fro.org.froproject.mvp.model;

import android.app.Application;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;

import static com.jess.arms.utils.Preconditions.checkNotNull;

import com.jess.arms.di.scope.ActivityScope;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import fro.org.froproject.app.MyApplication;
import fro.org.froproject.mvp.contract.ExerciseContract;
import fro.org.froproject.mvp.model.api.service.CommonService;
import fro.org.froproject.mvp.model.entity.BaseJson;
import fro.org.froproject.mvp.model.entity.CourseResponseBean;
import fro.org.froproject.mvp.model.entity.ExerciseBean;
import fro.org.froproject.mvp.model.entity.ExerciseCommitBean;
import fro.org.froproject.mvp.model.entity.ExerciseResponseBean;
import io.reactivex.Observable;
import okhttp3.MediaType;
import okhttp3.RequestBody;


/**
 * Created by Lgm on 2017/6/14 0014.
 */

@ActivityScope
public class ExerciseModel extends BaseModel implements ExerciseContract.Model {
    private Gson mGson;
    private Application mApplication;

    @Inject
    public ExerciseModel(IRepositoryManager repositoryManager, Gson gson, Application application) {
        super(repositoryManager);
        this.mGson = gson;
        this.mApplication = application;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }

    @Override
    public Observable<BaseJson<List<ExerciseBean>>> getQuestionList(int classId, int courseId) {
        Observable<BaseJson<List<ExerciseBean>>> response = mRepositoryManager.obtainRetrofitService(CommonService.class).getQuestionList(classId, courseId, MyApplication.getInstance().getToken());
        return response;

    }

    @Override
    public Observable<BaseJson<ExerciseResponseBean>> commit(int classId,int courseId,Map<String, Object> resultMap) {
        ArrayList arrayList = new ArrayList<>();
        for (String in : resultMap.keySet()) {
            arrayList.add(resultMap.get(in));//得到每个key多对用value的值
        }
        ExerciseCommitBean mExerciseCommitBean = new ExerciseCommitBean();
        mExerciseCommitBean.setCourseId(courseId);
        mExerciseCommitBean.setClassId(classId);
        mExerciseCommitBean.setExercises(arrayList);
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), mGson.toJson(mExerciseCommitBean));
        Observable<BaseJson<ExerciseResponseBean>> response = mRepositoryManager.obtainRetrofitService(CommonService.class).commitExercise(body, MyApplication.getInstance().getToken());
        return response;
    }
}
