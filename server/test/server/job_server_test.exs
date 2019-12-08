defmodule Server.JobServerTest do
  use Server.DataCase

  alias Server.JobServer

  describe "curriculuns" do
    alias Server.JobServer.Curriculum

    @valid_attrs %{area: "some area", contato: "some contato", nome: "some nome", salario: 42, tempo: 42}
    @update_attrs %{area: "some updated area", contato: "some updated contato", nome: "some updated nome", salario: 43, tempo: 43}
    @invalid_attrs %{area: nil, contato: nil, nome: nil, salario: nil, tempo: nil}

    def curriculum_fixture(attrs \\ %{}) do
      {:ok, curriculum} =
        attrs
        |> Enum.into(@valid_attrs)
        |> JobServer.create_curriculum()

      curriculum
    end

    test "list_curriculuns/0 returns all curriculuns" do
      curriculum = curriculum_fixture()
      assert JobServer.list_curriculuns() == [curriculum]
    end

    test "get_curriculum!/1 returns the curriculum with given id" do
      curriculum = curriculum_fixture()
      assert JobServer.get_curriculum!(curriculum.id) == curriculum
    end

    test "create_curriculum/1 with valid data creates a curriculum" do
      assert {:ok, %Curriculum{} = curriculum} = JobServer.create_curriculum(@valid_attrs)
      assert curriculum.area == "some area"
      assert curriculum.contato == "some contato"
      assert curriculum.nome == "some nome"
      assert curriculum.salario == 42
      assert curriculum.tempo == 42
    end

    test "create_curriculum/1 with invalid data returns error changeset" do
      assert {:error, %Ecto.Changeset{}} = JobServer.create_curriculum(@invalid_attrs)
    end

    test "update_curriculum/2 with valid data updates the curriculum" do
      curriculum = curriculum_fixture()
      assert {:ok, %Curriculum{} = curriculum} = JobServer.update_curriculum(curriculum, @update_attrs)
      assert curriculum.area == "some updated area"
      assert curriculum.contato == "some updated contato"
      assert curriculum.nome == "some updated nome"
      assert curriculum.salario == 43
      assert curriculum.tempo == 43
    end

    test "update_curriculum/2 with invalid data returns error changeset" do
      curriculum = curriculum_fixture()
      assert {:error, %Ecto.Changeset{}} = JobServer.update_curriculum(curriculum, @invalid_attrs)
      assert curriculum == JobServer.get_curriculum!(curriculum.id)
    end

    test "delete_curriculum/1 deletes the curriculum" do
      curriculum = curriculum_fixture()
      assert {:ok, %Curriculum{}} = JobServer.delete_curriculum(curriculum)
      assert_raise Ecto.NoResultsError, fn -> JobServer.get_curriculum!(curriculum.id) end
    end

    test "change_curriculum/1 returns a curriculum changeset" do
      curriculum = curriculum_fixture()
      assert %Ecto.Changeset{} = JobServer.change_curriculum(curriculum)
    end
  end

  describe "opportunities" do
    alias Server.JobServer.JobOpportunity

    @valid_attrs %{area: "some area", contato: "some contato", nomeEmpresa: "some nomeEmpresa", salario: 42, tempo: 42}
    @update_attrs %{area: "some updated area", contato: "some updated contato", nomeEmpresa: "some updated nomeEmpresa", salario: 43, tempo: 43}
    @invalid_attrs %{area: nil, contato: nil, nomeEmpresa: nil, salario: nil, tempo: nil}

    def job_opportunity_fixture(attrs \\ %{}) do
      {:ok, job_opportunity} =
        attrs
        |> Enum.into(@valid_attrs)
        |> JobServer.create_job_opportunity()

      job_opportunity
    end

    test "list_opportunities/0 returns all opportunities" do
      job_opportunity = job_opportunity_fixture()
      assert JobServer.list_opportunities() == [job_opportunity]
    end

    test "get_job_opportunity!/1 returns the job_opportunity with given id" do
      job_opportunity = job_opportunity_fixture()
      assert JobServer.get_job_opportunity!(job_opportunity.id) == job_opportunity
    end

    test "create_job_opportunity/1 with valid data creates a job_opportunity" do
      assert {:ok, %JobOpportunity{} = job_opportunity} = JobServer.create_job_opportunity(@valid_attrs)
      assert job_opportunity.area == "some area"
      assert job_opportunity.contato == "some contato"
      assert job_opportunity.nomeEmpresa == "some nomeEmpresa"
      assert job_opportunity.salario == 42
      assert job_opportunity.tempo == 42
    end

    test "create_job_opportunity/1 with invalid data returns error changeset" do
      assert {:error, %Ecto.Changeset{}} = JobServer.create_job_opportunity(@invalid_attrs)
    end

    test "update_job_opportunity/2 with valid data updates the job_opportunity" do
      job_opportunity = job_opportunity_fixture()
      assert {:ok, %JobOpportunity{} = job_opportunity} = JobServer.update_job_opportunity(job_opportunity, @update_attrs)
      assert job_opportunity.area == "some updated area"
      assert job_opportunity.contato == "some updated contato"
      assert job_opportunity.nomeEmpresa == "some updated nomeEmpresa"
      assert job_opportunity.salario == 43
      assert job_opportunity.tempo == 43
    end

    test "update_job_opportunity/2 with invalid data returns error changeset" do
      job_opportunity = job_opportunity_fixture()
      assert {:error, %Ecto.Changeset{}} = JobServer.update_job_opportunity(job_opportunity, @invalid_attrs)
      assert job_opportunity == JobServer.get_job_opportunity!(job_opportunity.id)
    end

    test "delete_job_opportunity/1 deletes the job_opportunity" do
      job_opportunity = job_opportunity_fixture()
      assert {:ok, %JobOpportunity{}} = JobServer.delete_job_opportunity(job_opportunity)
      assert_raise Ecto.NoResultsError, fn -> JobServer.get_job_opportunity!(job_opportunity.id) end
    end

    test "change_job_opportunity/1 returns a job_opportunity changeset" do
      job_opportunity = job_opportunity_fixture()
      assert %Ecto.Changeset{} = JobServer.change_job_opportunity(job_opportunity)
    end
  end
end
