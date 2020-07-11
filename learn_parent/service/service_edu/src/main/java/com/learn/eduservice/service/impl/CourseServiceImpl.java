package com.learn.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.learn.eduservice.entity.*;
import com.learn.eduservice.entity.form.CourseInfoForm;
import com.learn.eduservice.entity.query.CourseQuery;
import com.learn.eduservice.entity.vo.CoursePublishVo;
import com.learn.eduservice.entity.vo.CourseVo;
import com.learn.eduservice.entity.vo.WebCourseQueryVo;
import com.learn.eduservice.entity.vo.WebCourseVo;
import com.learn.eduservice.feign.OssService;
import com.learn.eduservice.mapper.*;
import com.learn.eduservice.service.CourseService;
import com.learn.service.base.dto.CourseDto;
import com.learn.service.base.exception.CustomException;
import com.learn.utils.result.ResponseResult;
import com.learn.utils.result.ResultCodeEnum;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author dlq
 * @since 2020-06-18
 */
@Service
public class CourseServiceImpl extends ServiceImpl<CourseMapper, Course> implements CourseService {

    @Autowired
    CourseDescriptionMapper courseDescriptionMapper;
    @Autowired
    private OssService ossService;
    @Autowired
    private VideoMapper videoMapper;
    @Autowired
    private ChapterMapper chapterMapper;
    @Autowired
    private CommentMapper commentMapper;
    @Autowired
    private CourseCollectMapper courseCollectMapper;
    @Autowired
    private TeacherMapper teacherMapper;
    @Autowired
    private CourseMapper courseMapper;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public String saveCourseInfo(CourseInfoForm courseInfoForm) {

        //保存Course
        Course course = new Course();
        BeanUtils.copyProperties(courseInfoForm, course);
        course.setStatus(Course.COURSE_DRAFT);
        baseMapper.insert(course);

        //保存CourseDescription
        CourseDescription courseDescription = new CourseDescription();
        courseDescription.setDescription(courseInfoForm.getDescription());
        courseDescription.setId(course.getId());
        courseDescriptionMapper.insert(courseDescription);

        return course.getId();
    }

    @Override
    public CourseInfoForm getCourseInfoById(String id) {
        //根据id获取Course
        Course course = baseMapper.selectById(id);
        if (course == null) {
            return null;
        }

        //根据id获取CourseDescription
        CourseDescription courseDescription = courseDescriptionMapper.selectById(id);

        //组装成CourseInfoForm
        CourseInfoForm courseInfoForm = new CourseInfoForm();
        BeanUtils.copyProperties(course, courseInfoForm);
        courseInfoForm.setDescription(courseDescription.getDescription());

        return courseInfoForm;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateCourseInfoById(CourseInfoForm courseInfoForm) {

        /*//保存课程基本信息
        Course course = new Course();
        BeanUtils.copyProperties(courseInfoForm, course);
        baseMapper.updateById(course);*/

        String id = courseInfoForm.getId();
        Course selectCourseById = courseMapper.selectById(id);
        if (StringUtils.isEmpty(selectCourseById)){
            throw new CustomException(ResultCodeEnum.COURSE_IS_NULL);
        }
        //更新Course
        selectCourseById.setId(courseInfoForm.getId());
        selectCourseById.setTeacherId(courseInfoForm.getTeacherId());
        selectCourseById.setSubjectId(courseInfoForm.getSubjectId());
        selectCourseById.setSubjectParentId(courseInfoForm.getSubjectParentId());
        selectCourseById.setTitle(courseInfoForm.getTitle());
        selectCourseById.setPrice(courseInfoForm.getPrice());
        selectCourseById.setLessonNum(courseInfoForm.getLessonNum());
        selectCourseById.setCover(courseInfoForm.getCover());
        courseMapper.updateById(selectCourseById);

        //更新CourseDescription
        CourseDescription courseDescription = new CourseDescription();
        courseDescription.setDescription(courseInfoForm.getDescription());
        courseDescription.setId(courseInfoForm.getId());
        courseDescriptionMapper.updateById(courseDescription);
    }

    @Override
    public Page<CourseVo> selectPage(long page, long size, CourseQuery courseQuery) {

        //组装查询条件
        QueryWrapper<CourseVo> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("c.gmt_create");
        String title = courseQuery.getTitle();
        String teacherId = courseQuery.getTeacherId();
        String subjectParentId = courseQuery.getSubjectParentId();
        String subjectId = courseQuery.getSubjectId();
        if (!StringUtils.isEmpty(title)) {
            queryWrapper.like("c.title", title);
        }
        if (!StringUtils.isEmpty(teacherId)) {
            queryWrapper.eq("c.teacher_id", teacherId);
        }
        if (!StringUtils.isEmpty(subjectParentId)) {
            queryWrapper.eq("c.subject_parent_id", subjectParentId);
        }
        if (!StringUtils.isEmpty(subjectId)) {
            queryWrapper.eq("c.subject_id", subjectId);
        }

        //组装分页
        Page<CourseVo> pageParam = new Page<>(page, size);
        //执行分页查询
        //只需要在mapper层传入封装好的分页组件即可，sql分页条件组装的过程mp自动完成
        List<CourseVo> records = baseMapper.selectPageByCourseQuery(pageParam, queryWrapper);
        //将 records 设置到 pageParam 中
        return pageParam.setRecords(records);
    }

    @Override
    public boolean removeCoverById(String id) {
        //根据id获取讲师 cover 的url
        Course course = baseMapper.selectById(id);
        if (course != null) {
            String cover = course.getCover();
            if (!StringUtils.isEmpty(cover)) {
                ResponseResult result = ossService.removeFile(cover);
                return result.getSuccess();
            }
        }
        return false;
    }

    /**
     * 数据库中外键约束的设置，
     * 互联网分布式项目中不允许使用外键与级联更新，一切设计级联的操作不要依赖数据库层，要在业务层解决
     * <p>
     * 如果业务层解决级联删除功能
     * 那么先删除字表数据，再删除父表数据
     *
     * @param id
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean removeCourseById(String id) {

        //根据courseId删除Video(课时)
        QueryWrapper<Video> videoQueryWrapper = new QueryWrapper<>();
        videoQueryWrapper.eq("course_id", id);
        videoMapper.delete(videoQueryWrapper);

        //根据courseId删除Chapter(章节)
        QueryWrapper<Chapter> chapterQueryWrapper = new QueryWrapper<>();
        chapterQueryWrapper.eq("course_id", id);
        chapterMapper.delete(chapterQueryWrapper);

        //根据courseId删除comment(评论)
        QueryWrapper<Comment> commentQueryWrapper = new QueryWrapper<>();
        commentQueryWrapper.eq("course_id", id);
        commentMapper.delete(commentQueryWrapper);

        //根据courseId删除CourseCollect(课程收藏)
        QueryWrapper<CourseCollect> courseCollectQueryWrapper = new QueryWrapper<>();
        courseCollectQueryWrapper.eq("course_id", id);
        courseCollectMapper.delete(courseCollectQueryWrapper);

        //根据courseId删除CourseDescription(课程描述)
//        QueryWrapper<CourseDescription> courseDescriptionQueryWrapper = new QueryWrapper<>();
//        courseDescriptionQueryWrapper.eq("id",id);
//        courseDescriptionMapper.delete(courseDescriptionQueryWrapper);
        courseDescriptionMapper.deleteById(id);

        //删除课程
        return this.removeById(id);
    }

    @Override
    public CoursePublishVo getCoursePublishVoById(String id) {
        return baseMapper.selectCoursePublishVoById(id);
    }

    @Override
    public boolean publishCourseById(String id) {
        Course course = new Course();
        course.setId(id);
        course.setStatus(Course.COURSE_NORMAL);
        return this.updateById(course);
    }

    @Override
    public List<Course> webSelectList(WebCourseQueryVo webCourseQueryVo) {
        QueryWrapper<Course> queryWrapper = new QueryWrapper<>();

        //查询已发布的课程 ，，，没发布的查出来展示？？？
        queryWrapper.eq("status", Course.COURSE_NORMAL);
        if (!StringUtils.isEmpty(webCourseQueryVo.getSubjectParentId())) {
            queryWrapper.eq("subject_parent_id", webCourseQueryVo.getSubjectParentId());
        }

        if (!StringUtils.isEmpty(webCourseQueryVo.getSubjectId())) {
            queryWrapper.eq("subject_id", webCourseQueryVo.getSubjectId());
        }

        if (!StringUtils.isEmpty(webCourseQueryVo.getBuyCountSort())) {
            queryWrapper.orderByDesc("buy_count");
        }

        if (!StringUtils.isEmpty(webCourseQueryVo.getGmtCreateSort())) {
            queryWrapper.orderByDesc("gmt_create");
        }

        if (!StringUtils.isEmpty(webCourseQueryVo.getPriceSort())) {
            if (webCourseQueryVo.getType() == null || webCourseQueryVo.getType() == 1) {
                queryWrapper.orderByAsc("price");
            } else {
                queryWrapper.orderByDesc("price");
            }
        }

        return baseMapper.selectList(queryWrapper);
    }

    /**
     * 获取课程信息并更新浏览量
     *
     * @param id
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public WebCourseVo selectWebCourseVoById(String id) {
        Course course = baseMapper.selectById(id);
        //更新浏览数+1
        course.setViewCount(course.getViewCount() + 1);
        baseMapper.updateById(course);
        //获取课程信息
        return baseMapper.selectWebCourseVoById(id);
    }

    @Cacheable(value = "index",key = "'selectHotCourse'")
    @Override
    public List<Course> selectHotCourse() {
        QueryWrapper<Course> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("view_count");
        queryWrapper.last("limit 8");
        return baseMapper.selectList(queryWrapper);
    }

    @Override
    public CourseDto getCourseDtoById(String courseId) {

        //这种方法分别获取teacher和course数据进行dto组装，此方法执行两条sql语句，查询性能---较弱
//        Course course = baseMapper.selectById(courseId);
//        String teacherId = course.getTeacherId();
//        Teacher teacher = teacherMapper.selectById(teacherId);
//
//        CourseDto courseDto = new CourseDto();
//        courseDto.setId(course.getId());
//        courseDto.setCover(course.getCover());
//        courseDto.setPrice(course.getPrice());
//        courseDto.setTitle(course.getTitle());
//        courseDto.setTeacherName(teacher.getName());
//
//        return courseDto;

        //在xml文件手写sql语句 ----->此方法只执行一条sql语句--->查询性能较强---->强烈推荐手写sql
        return baseMapper.selectCourseDtoById(courseId);
    }

    @Override
    public void updateBuyCountById(String id) {
        Course course = baseMapper.selectById(id);
        long buyCount = course.getBuyCount()+1;
        course.setBuyCount(buyCount);
        baseMapper.updateById(course);
    }

}
